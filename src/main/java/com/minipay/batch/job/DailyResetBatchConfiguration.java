package com.minipay.batch.job;

import com.minipay.daliyLimit.domain.DailyLimit;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class DailyResetBatchConfiguration {

    private final EntityManagerFactory entityManagerFactory;

    @Bean
    public Job dailyLimitResetJob(JobRepository jobRepository, Step dailyLimitResetStep) {
        return new JobBuilder("dailyLimitResetJob", jobRepository)
                .start(dailyLimitResetStep)
                .build();
    }

    @Bean
    public Step dailyLimitResetStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder( "dailyLimitResetStep", jobRepository)
                .<DailyLimit, DailyLimit>chunk(10, transactionManager)
                .reader(dailyLimitResetReader())
                .processor(dailyLimitResetProcessor())
                .writer(dailyLimitResetWriter())
                .build();
    }

    @Bean
    public JpaCursorItemReader<DailyLimit> dailyLimitResetReader() {

        return new JpaCursorItemReaderBuilder<DailyLimit>()
                .name("dailyLimitResetReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT d FROM DailyLimit d WHERE d.dailyTotalBalance != 0")
                .build();
    }

    @Bean
    public ItemProcessor<DailyLimit, DailyLimit> dailyLimitResetProcessor() {
        return dailyLimit -> {
            dailyLimit.resetDailyBalance();
            return dailyLimit;
        };
    }

    @Bean
    public JpaItemWriter<DailyLimit> dailyLimitResetWriter() {
        return new JpaItemWriterBuilder<DailyLimit>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

}