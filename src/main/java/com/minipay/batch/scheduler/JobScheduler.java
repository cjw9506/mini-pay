package com.minipay.batch.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobScheduler {

    private final JobLauncher jobLauncher;

    @Qualifier("dailyLimitResetJob")
    private final Job dailyLimitResetJob;
    @Qualifier("interestJob")
    private final Job interestJob;
    @Qualifier("transferToSavingsJob")
    private final Job transferToSavingsJob;



    //재시도 로직
    //chunk, page 사이즈 고민
    //

    @Scheduled(cron = "00 28 20 * * ?")
    public void dailyResetRun() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(dailyLimitResetJob, jobParameters);
    }

    @Scheduled(cron = "50 04 20 * * ?")
    public void interestRun() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(interestJob, jobParameters);
    }

    @Scheduled(cron = "00 17 00 * * ?")
    public void transferSavingsRun() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(transferToSavingsJob, jobParameters);
    }

}

