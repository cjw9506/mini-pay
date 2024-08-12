package com.minipay.batch.job;

import com.minipay.account.domain.Account;
import com.minipay.account.domain.Type;
import com.minipay.account.repository.AccountRepository;

import com.minipay.batch.dto.AccountProcessingResult;
import com.minipay.deposit.domain.Deposit;
import com.minipay.deposit.repository.DepositRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDateTime;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class TransferToSavingsBatchConfiguration {

    private final EntityManagerFactory entityManagerFactory;
    private final AccountRepository accountRepository;
    private final DepositRepository depositRepository;
    private final EntityManager entityManager;

    @Bean
    public Job transferToSavingsJob(JobRepository jobRepository, Step transferToSavingsStep) {
        return new JobBuilder("transferToSavingsJob", jobRepository)
                .start(transferToSavingsStep)
                .build();
    }

    @Bean
    public Step transferToSavingsStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder( "transferToSavingsStep", jobRepository)
                .<Account, AccountProcessingResult>chunk(10, transactionManager)
                .reader(transferToSavingsReader())
                .processor(transferToSavingsProcessor())
                .writer(transferToSavingsWriter())
                .build();
    }

    @Bean
    public JpaCursorItemReader<Account> transferToSavingsReader() {

        return new JpaCursorItemReaderBuilder<Account>()
                .name("transferToSavingsReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT a FROM Account a WHERE a.type = 'REGULAR_SAVING'")
                .build();
    }

    @Bean
    public ItemProcessor<Account, AccountProcessingResult> transferToSavingsProcessor() {
        return savingAccount -> {
            Account mainAccount = accountRepository.findByUserIdAndType(savingAccount.getUser().getId(), Type.MAIN);
            log.info("{}번 계좌 조회", mainAccount.getId());

            long regularFee = savingAccount.getRegularFee();
            long mainBalance = mainAccount.getBalance();

            if (regularFee > mainBalance) {
                long chargeAmount = (((regularFee + 9999 - mainBalance) / 10000) * 10000); //충전금액

                Deposit deposit = Deposit.builder()
                        .account(mainAccount)
                        .amount(chargeAmount)
                        .timeStamp(LocalDateTime.now())
                        .build();

                depositRepository.save(deposit);
                mainAccount.deposit(-regularFee + chargeAmount);
            } else {
                mainAccount.deposit(-regularFee);
                log.info("{}번 메인계좌 돈 minus", mainAccount.getId());
            }
            savingAccount.deposit(regularFee);
            log.info("{}번 적금계좌 돈 plus", savingAccount.getId());

            return AccountProcessingResult.builder()
                    .mainAccount(mainAccount)
                    .savingAccount(savingAccount)
                    .build();
        };
    }


    @Bean
    public ItemWriter<AccountProcessingResult> transferToSavingsWriter() {
        return results -> {
            for (AccountProcessingResult result : results) {
                Account mainAccount = result.getMainAccount();
                Account savingAccount = result.getSavingAccount();

                entityManager.merge(mainAccount);
                entityManager.merge(savingAccount);
            }
        };

    }

}
