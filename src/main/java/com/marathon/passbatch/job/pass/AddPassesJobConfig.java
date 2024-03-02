package com.marathon.passbatch.job.pass;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class AddPassesJobConfig {

    private final AddPassesTasklet addPassesTasklet;

    public AddPassesJobConfig(AddPassesTasklet addPassesTasklet) {
        this.addPassesTasklet = addPassesTasklet;
    }

    @Bean
    public Job addPassesJob(JobRepository jobRepository, Step addPassesStep) {

        return new JobBuilder("addPassesJob", jobRepository)
                    .start(addPassesStep)
                    .build();
    }

    @Bean
    public Step addPassesStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {

        /**
         * PlatformTransactionManager : 스프링배치에서 데이터베이스 트랜잭션을 시작하고 관리하는데 사용.
         * 이를 통해 데이터 일관성을 유지함.(일관성 유지, 예외처리, 병렬 처리)
         *
         * Tasklet : Step 안에서 수행될 기능들이 명시되어 있는 메소드.
         *
         * AddPassesTasklet 클래스 내의 메서드에 어떻게 실행될지 로직이 구현되어있다.
         * */
        return new StepBuilder("addPassesStep", jobRepository)
                    .tasklet(addPassesTasklet, platformTransactionManager)
                    .build();
    }

}
