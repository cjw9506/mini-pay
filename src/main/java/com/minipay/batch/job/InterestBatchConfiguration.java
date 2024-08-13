package com.minipay.batch.job;

import com.minipay.account.domain.Account;
import com.minipay.account.domain.Type;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class InterestBatchConfiguration {

    private final EntityManagerFactory entityManagerFactory;

    @Bean
    public Job interestJob(JobRepository jobRepository, Step interestStep) {
        return new JobBuilder("interestJob", jobRepository)
                .start(interestStep)
                .build();
    }

    @Bean
    public Step interestStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder( "interestStep", jobRepository)
                .<Account, Account>chunk(10, transactionManager)
                .reader(interestReader())
                .processor(interestProcessor())
                .writer(interestWriter())
                .build();
    }

    @Bean
    public JpaCursorItemReader<Account> interestReader() {

        return new JpaCursorItemReaderBuilder<Account>()
                .name("interestReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT a FROM Account a WHERE a.type IN ('REGULAR_SAVING', 'FREE_SAVING')")
                .build();
    }

    @Bean
    public ItemProcessor<Account, Account> interestProcessor() {
        return account -> {
            if (Type.FREE_SAVING == account.getType()) {
                long interest = (long) Math.ceil((account.getBalance() / 100) * 3);
                account.addInterest(interest);
            } else if (Type.REGULAR_SAVING == account.getType()) {
                long interest = (long) Math.ceil((account.getBalance() / 100) * 5);
                account.addInterest(interest);
            }
            return account;
        };
    }

    @Bean
    public ItemWriter<Account> interestWriter() {
        return new JpaItemWriterBuilder<Account>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

}
