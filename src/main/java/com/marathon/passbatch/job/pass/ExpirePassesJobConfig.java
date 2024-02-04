package com.marathon.passbatch.job.pass;

import java.time.LocalDateTime;
import java.util.Map;

import com.marathon.passbatch.repository.pass.PassEntity;
import com.marathon.passbatch.repository.pass.PassStatus;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
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
public class ExpirePassesJobConfig {

    private final int CHUNK_SIZE = 5;
    private final EntityManagerFactory entityManagerFactory;

    public ExpirePassesJobConfig(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }
    // = Persistence.createEntityManagerFactory("com.marathon.passbatch.repository.pass.PassEntity");

    /**
     * Job은 배치 프로세스의 최상위 개념. 하나 이상의 Step을 포함한다.
     * 배치작업의 실행 단위를 뜻함.
     * */
    @Bean
    public Job expirePassesJob(JobRepository jobRepository, Step expirePassesStep) {
        return new JobBuilder("expirePassesJob", jobRepository)
                    .start(expirePassesStep)
                    .build();
    }

    /**
     * Step : 배치작업의 단일 처리 단위를 나타낸다.
     * 데이터 처리 비즈니스 로직이 여기서 실행된다.
     * (ItemReader, ItemProcessor, ItemWriter가 여기에 포함)
     * */
    @Bean
    public Step expirePassesStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("expirePassesStep", jobRepository)
                    .<PassEntity, PassEntity>chunk(CHUNK_SIZE, platformTransactionManager)
                    .reader(expirePassesItemReader())
                    .processor(expirePassesItemProcessor())
                    .writer(expirePassesItemWriter())
                    .build();

    }

    /**
     * ItemReader : 데이터를 읽어오는 역할. 데이터를 읽어서 ItemProcessor에 전달.
     * */
    @Bean
    @StepScope
    public JpaCursorItemReader<PassEntity> expirePassesItemReader() {
        return new JpaCursorItemReaderBuilder<PassEntity>()
                    .name("expirePassesItemReader")
                    .entityManagerFactory(entityManagerFactory)
                    .queryString("select p from PassEntity p where p.status = :status and p.endedAt <= :endedAt")
                    .parameterValues(Map.of("status", PassStatus.PROGRESSED, "endedAt", LocalDateTime.now()))
                    .build();
    }


    /**
     * ItmeProcessor : 읽어온 데이터를 가공, 변환하는 역할
     * 비즈니스 로직을 적용해서 처리 후, ItemWriter에 전달한다.
     * */
    @Bean
    public ItemProcessor<PassEntity, PassEntity> expirePassesItemProcessor() {
        return passEntity -> {
            passEntity.setStatus(PassStatus.EXPIRED);
            passEntity.setExpiredAt(LocalDateTime.now());
            return passEntity;
        };
    }

    /**
     * ItemWriter : 가공된 데이터를 저장하거나 외부시스템에 전달.
     * DB에 쓰거나 함
     * */
    @Bean
    public JpaItemWriter<PassEntity> expirePassesItemWriter() {
        return new JpaItemWriterBuilder<PassEntity>()
                    .entityManagerFactory(entityManagerFactory)
                    .build();
    }
}
