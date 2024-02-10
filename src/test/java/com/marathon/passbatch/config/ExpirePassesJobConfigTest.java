package com.marathon.passbatch.config;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.marathon.passbatch.job.pass.ExpirePassesJobConfig;
import com.marathon.passbatch.repository.pass.PassEntity;
import com.marathon.passbatch.repository.pass.PassRepository;
import com.marathon.passbatch.repository.pass.PassStatus;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.batch.core.ExitStatus;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import com.marathon.passbatch.repository.pass.PassRepository;

@Slf4j
@SpringBatchTest
@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = {ExpirePassesJobConfig.class, TestBatchConfig.class})
public class ExpirePassesJobConfigTest {

    /**
     * @SpringBatchTest : 스프링 Job, Step을 테스트할 때 사용하는 어노테이션
     * @ActiveProfiles : application-{profile}.yml 형식으로 test 설정파일 찾아서 사용
     * @ContextConfiguration : 설정파일을 참조할 때 사용하는 어노테이션. xml 파일로 대체 가능.
     * */

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    /**
     * @EntityScan("com.marathon.passbatch.repository.pass")
     * @EnableJpaRepositories("com.marathon.passbatch.repository.pass")
     * TestBatchConfig 에 설정한 위 2개 어노테이션이 PassRespository를 참조시켜준다.
     * EntityScan : JPA 엔티티 클래스를 찾아서 SpringApplicationContext에 등록하도록 지시.
     * *Jpa 엔티티 : 데이터베이스 테이블과 맵핑되는 객체를 정의하는 클래스
     *
     * EnableJpaRepositories : JPA 리포지토리 인터페이스를 찾아서 SpringApplicationContext에 등록하도록 지시.
     * JpaRepository : DB와 상호작용하는 메서드를 정의하는 인터페이스
     *
     * */
    @Autowired
    private PassRepository passRepository;


    @Test
    public void test_expirePassesStep() throws Exception {
        // given
        addPassesEntites(5);


        // when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
        JobInstance jobInstance = jobExecution.getJobInstance();

        // then
        assertEquals(ExitStatus.COMPLETED.toString(), jobExecution.getExitStatus(), jobExecution.getExitStatus());
        assertEquals("expirePassesJob", jobInstance.getJobName(), jobInstance.getJobName());


    }

    private void addPassesEntites(int size) {
        final LocalDateTime now = LocalDateTime.now();
        final Random random = new Random();

        List<PassEntity> passEntityList = new ArrayList<>();
        for(int i=0; i<size; i++) {
            PassEntity passEntity = new PassEntity();
            passEntity.setPackageSeq(1);
            passEntity.setUserId("A" + 1000000 + i);
            passEntity.setStatus(PassStatus.PROGRESSED);
            passEntity.setRemainingCount(random.nextInt(11));
            passEntity.setStartedAt(now.minusDays(60));
            passEntity.setEndedAt(now.minusDays(1));
            passEntityList.add(passEntity);
        }

        passRepository.saveAll(passEntityList);
    }



















}
