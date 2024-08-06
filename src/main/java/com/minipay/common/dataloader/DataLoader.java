package com.minipay.common.dataloader;

import com.minipay.account.domain.Account;
import com.minipay.account.domain.Type;
import com.minipay.account.dto.DepositDTO;
import com.minipay.account.repository.AccountRepository;
import com.minipay.account.service.AccountService;
import com.minipay.transaction.domain.Transaction;
import com.minipay.transaction.repository.TransactionRepository;
import com.minipay.user.domain.User;
import com.minipay.user.dto.SignupDTO;
import com.minipay.user.repository.UserRepository;
import com.minipay.user.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
@Component
public class DataLoader {

    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;


    @PostConstruct
    public void init() {
        //addUsers();
        //depositMoneyInMainAccount();
        //createSavingAccount();
        //createRemittanceHistory();
    }

    public void addUsers() {
        for (int i = 1; i <= 100000; i++) {
            String username = "test" + i;
            String password = "testPassword";
            SignupDTO user = SignupDTO.builder()
                    .username(username)
                    .password(password)
                    .build();
            userService.save(user);
        }
    }

    public void depositMoneyInMainAccount() {
        for (int i = 1; i <= 100000; i++) {
            DepositDTO deposit = DepositDTO.builder()
                    .accountId((long) i)
                    .balance(1000000)
                    .build();
            accountService.deposit(deposit);
        }
    }

    public void createSavingAccount() {
        for (int i = 1; i <= 50000; i++) {

            User user = userRepository.findById((long) i).orElseThrow();
            Account regularSavingAccount = Account.builder()
                    .user(user)
                    .balance(100000)
                    .type(Type.REGULAR_SAVING)
                    .regularFee(50000)
                    .build();

            accountRepository.save(regularSavingAccount);
        }

        for (int i = 50001; i <= 100000; i++) {
            User user = userRepository.findById((long) i).orElseThrow();

            Account freeSavingAccount = Account.builder()
                    .user(user)
                    .balance(100000)
                    .type(Type.FREE_SAVING)
                    .regularFee(0)
                    .build();

            accountRepository.save(freeSavingAccount);
        }
    }

    public void createRemittanceHistory() {

        Account sender = accountRepository.findById(20000L).orElseThrow();
        Account receiver = accountRepository.findById(40000L).orElseThrow();

        for (int i = 1; i <= 200000; i++) {
            Transaction transaction = Transaction.builder()
                    .senderAccount(sender)
                    .receiverAccount(receiver)
                    .amount(100000L)
                    .sourceOrDestination("용돈")
                    .timeStamp(LocalDateTime.now())
                    .build();

            transactionRepository.save(transaction);
        }

        Account sender1 = accountRepository.findById(50000L).orElseThrow();
        Account receiver1 = accountRepository.findById(70000L).orElseThrow();

        for (int i = 1; i <= 200000; i++) {
            Transaction transaction = Transaction.builder()
                    .senderAccount(sender1)
                    .receiverAccount(receiver1)
                    .amount(100000L)
                    .sourceOrDestination("용돈")
                    .timeStamp(LocalDateTime.now())
                    .build();

            transactionRepository.save(transaction);
        }

    }
}


