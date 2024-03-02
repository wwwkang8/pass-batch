package com.marathon.passbatch.job.pass;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class AddPassesJobConfig_T {

    private final AddPassesTasklet_T addPassesTasklet_t;

    public AddPassesJobConfig_T(AddPassesTasklet_T addPassesTasklet_t) {
        this.addPassesTasklet_t = addPassesTasklet_t;
    }

    @Bean
    public Job addPassesJob(JobRepository jobRepository, Step addPassesStep) {

        return new JobBuilder("addPassesJob", jobRepository)
                    .start(addPassesStep)
                    .build();
    }

    public Step addPassesStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {

        return new StepBuilder("addPassesStep", jobRepository)
                    .tasklet(addPassesTasklet_t, platformTransactionManager)
                    .build();
    }

}
