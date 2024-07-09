package com.minipay.account.service;

import com.minipay.account.domain.Account;
import com.minipay.account.dto.AccountDTO;
import com.minipay.account.dto.DepositDTO;
import com.minipay.account.dto.WithdrawalDTO;
import com.minipay.account.repository.AccountRepository;
import com.minipay.deposit.domain.Deposit;
import com.minipay.deposit.repository.DepositRepository;
import com.minipay.user.domain.User;
import com.minipay.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {

    private final AccountRepository accountRepository;
    private final DepositRepository depositRepository;
    private final UserRepository userRepository;

    private static final long TODAY_LIMIT = 3000000L;

    @Transactional
    public void deposit(DepositDTO request) {

        LocalDate today = LocalDate.now();
        Account account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new IllegalArgumentException("계좌를 찾을 수 없습니다."));

        List<Deposit> deposits = depositRepository.findDepositsForToday(request.getAccountId(), today);
        long totalDeposit = deposits.stream().mapToLong(Deposit::getAmount).sum();

        if (totalDeposit + request.getBalance() > TODAY_LIMIT) {
            throw new IllegalArgumentException("일일 입금 금액 초과");
        }

        Deposit deposit = Deposit.builder()
                .account(account)
                .amount(request.getBalance())
                .timeStamp(today)
                .build();

        account.deposit(request.getBalance());
        depositRepository.save(deposit);

    }

    @Transactional
    public void addAccount(AccountDTO request) {

        User user = userRepository.findById(request.getUserId()).orElseThrow(IllegalArgumentException::new);

        Account newAccount = Account.builder()
                .user(user)
                .type(request.getType())
                .balance(0L)
                .build();

        accountRepository.save(newAccount);
    }


    @Transactional
    public void withdrawal1(WithdrawalDTO request) {

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

}
