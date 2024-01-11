package com.marathon.passbatch.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@Configuration
public class JpaConfig {

    // @EnableJpaAuditing : 공통적으로 가진 테이블 필드 생성일시, 수정일시 같은 것들을 자동으로 값을 넣어주는 것.
    // 자동으로 시간을 맵핑해서 DB 테이블에 넣어준다.

}
