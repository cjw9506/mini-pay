package com.minipay.account.service;

import com.minipay.account.domain.Account;
import com.minipay.account.domain.Type;
import com.minipay.account.dto.*;
import com.minipay.account.repository.AccountRepository;
import com.minipay.daliyLimit.domain.DailyLimit;
import com.minipay.daliyLimit.repository.DailyLimitRepository;
import com.minipay.deposit.domain.Deposit;
import com.minipay.deposit.repository.DepositRepository;
import com.minipay.transaction.domain.Transaction;
import com.minipay.transaction.repository.TransactionRepository;
import com.minipay.user.domain.User;
import com.minipay.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {

    private final AccountRepository accountRepository;
    private final DepositRepository depositRepository;
    private final UserRepository userRepository;
    private final DailyLimitRepository dailyLimitRepository;
    private final TransactionRepository transactionRepository;

    private static final long TODAY_LIMIT = 3000000L;

    @Transactional
    public void deposit(DepositDTO request) {

        Account account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new IllegalArgumentException("계좌를 찾을 수 없습니다."));

        Long userId = account.getUser().getId();
        DailyLimit dailyLimit = dailyLimitRepository.findById(userId).orElseThrow(IllegalArgumentException::new);
        long dailyLimitBalance= dailyLimit.getDailyTotalBalance();

        if (dailyLimitBalance + request.getBalance() > TODAY_LIMIT) {
            throw new IllegalArgumentException("일일 입금 금액 초과");
        }

        Deposit deposit = Deposit.builder()
                .account(account)
                .amount(request.getBalance())
                .timeStamp(LocalDateTime.now())
                .build();

        account.deposit(request.getBalance());
        depositRepository.save(deposit);

        dailyLimit.addBalance(request.getBalance());
    }

    @Transactional
    public void addAccount(AccountDTO request) {

        User user = userRepository.findById(request.getUserId()).orElseThrow(IllegalArgumentException::new);

        Account newAccount = Account.builder()
                .user(user)
                .type(request.getType())
                .balance(0L)
                .regularFee(request.getRegularFee())
                .build();

        accountRepository.save(newAccount);
    }


    @Transactional
    public void withdrawal(WithdrawalDTO request) {

        Account main = accountRepository.findById(request.getMainAccountId())
                .orElseThrow(() -> new IllegalArgumentException("메인 계좌를 찾을 수 없습니다."));
        Account saving = accountRepository.findById(request.getSavingAccountId())
                .orElseThrow(() -> new IllegalArgumentException("적금 계좌를 찾을 수 없습니다."));

        if (main.getBalance()  < request.getBalance()) {
            throw new IllegalArgumentException("메인계좌의 잔액이 부족합니다");
        }

        main.deposit(-request.getBalance());
        saving.deposit(request.getBalance());

    }

    @Transactional(readOnly = true)
    public List<GetAccountResponseDTO> getAccounts(Long userId) {

        List<Account> accounts = accountRepository.findAllByUserId(userId);

        return accounts.stream()
                .map(account -> GetAccountResponseDTO.builder()
                        .id(account.getId())
                        .balance(account.getBalance())
                        .type(account.getType())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public GetAccountResponseDTO getAccount(Long userId, Type type) {

        Account account = accountRepository.findByUserIdAndType(userId, type);

        return GetAccountResponseDTO
                .builder()
                .id(account.getId())
                .balance(account.getBalance())
                .type(account.getType())
                .build();
    }

    @Transactional
    public void remittance(RemittanceDTO request) {

        Account senderAccount = accountRepository.findByIdWithPessimisticLock(request.getSenderAccountId());
        Account receiverAccount = accountRepository.findByIdWithPessimisticLock(request.getReceiverAccountId());

        if (senderAccount.getBalance() >= request.getBalance()) { //가진돈이 보낼돈보다 많다면
            senderAccount.deposit(-request.getBalance());
            receiverAccount.deposit(request.getBalance());
        } else { //가진돈이 보낼돈보다 적다면
            long totalDeposit = getTotalDeposit(senderAccount);
            daliyLimitDetermination(request, totalDeposit, senderAccount, receiverAccount);
        }

        Transaction transaction = Transaction.builder()
                .senderAccount(senderAccount)
                .receiverAccount(receiverAccount)
                .amount(request.getBalance())
                .sourceOrDestination(request.getSourceOrDestination())
                .timeStamp(LocalDateTime.now())
                .build();

        transactionRepository.save(transaction);
    }

    @Transactional(readOnly = true)
    public List<TransactionsDTO> getRemittanceHistory(Long userId) {
        Account account = accountRepository.findByUserIdAndType(userId, Type.MAIN);

        List<Transaction> transactions = transactionRepository.findByReceiverOrSenderAccount(account);

        return transactions.stream()
                .map(transaction -> TransactionsDTO.builder()
                        .receiverId(transaction.getReceiverAccount().getId())
                        .senderId(transaction.getSenderAccount().getId())
                        .timeStamp(transaction.getTimeStamp())
                        .sourceOrDestination(transaction.getSourceOrDestination())
                        .amount(transaction.getAmount())
                        .build())
                .collect(Collectors.toList());
    }

    private void daliyLimitDetermination(RemittanceDTO request, long totalDeposit, Account senderAccount, Account receiverAccount) {
        if (totalDeposit + request.getBalance() > TODAY_LIMIT) { // 오늘충전금액 + 보낼금액 > 일일 충전 한도
            throw new IllegalArgumentException("일일 입금 금액 초과");
        } else {
            long chargeAmount = ((request.getBalance() + 9999 - senderAccount.getBalance()) / 10000) * 10000; //충전금액

            Deposit deposit = Deposit.builder()
                    .account(senderAccount)
                    .amount(chargeAmount)
                    .timeStamp(LocalDateTime.now())
                    .build();

            depositRepository.save(deposit);

            senderAccount.deposit(-request.getBalance() + chargeAmount);
            receiverAccount.deposit(request.getBalance());

        }
    }

    private long getTotalDeposit(Account senderAccount) {
        DailyLimit dailyLimit = dailyLimitRepository.findById(senderAccount.getId()).orElseThrow(IllegalArgumentException::new);
        return dailyLimit.getDailyTotalBalance();
    }

    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void dailyLimitReset() {
        List<DailyLimit> dailyLimitForAllUsers = dailyLimitRepository.findAll();
        dailyLimitForAllUsers.forEach(dailyLimit -> dailyLimit.resetDailyBalance(0));
    }

    @Scheduled(cron = "0 0 4 * * ?")
    @Transactional
    public void savingInterest() {
        List<Account> accounts = accountRepository.findAllSavingsAccounts();

        for (Account a : accounts) {
            if (a.getType() == Type.FREE_SAVING) {
                long interest = (long) Math.ceil((a.getBalance() / 100) * 3);
                a.addInterest(interest);
            } else if (a.getType() == Type.REGULAR_SAVING) {
                long interest = (long) Math.ceil((a.getBalance() / 100) * 5);
                a.addInterest(interest);
            }
        }
    }

    @Scheduled(cron = "0 0 8 * * ?")
    @Transactional
    public void withdrawalOfSaving() {
        List<Account> accounts = accountRepository.findAllSavingsAccounts();

        for (Account a : accounts) {
            if (a.getType() == Type.REGULAR_SAVING) {
                Account mainAccount = accountRepository.findByUserIdAndType(a.getUser().getId(), Type.MAIN);
                if (a.getRegularFee() > mainAccount.getBalance()) {
                    long chargeAmount = (((a.getRegularFee() + 9999 - mainAccount.getBalance()) / 10000) * 10000); //충전금액

                    Deposit deposit = Deposit.builder()
                            .account(a)
                            .amount(chargeAmount)
                            .timeStamp(LocalDateTime.now())
                            .build();

                    depositRepository.save(deposit);
                    mainAccount.deposit(-a.getRegularFee() + chargeAmount);
                    a.deposit(a.getRegularFee());
                } else {
                    mainAccount.deposit(-a.getRegularFee());
                    a.deposit(a.getRegularFee());
                }
            }
        }
    }


}
