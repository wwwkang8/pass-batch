package com.marathon.passbatch.config;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Configuration;

@EnableBatchProcessing
@Configuration
public class BatchConfig {

    /**
     * @EnableBatchProcessing : 스프링에서 배치작업 설정을 간소화함.
     * JobRepository, Transaction, JobLauncher, Job 설정들을 간편하게 도와
     *
     * @Configuration : 이 클래스가 스프링 설정 클래스임을 나타낸다.
     * 그래서 이 BatchConfig가 배치설정 클래스임을 선언하는 것이다.
     *
     *
     * */

}
