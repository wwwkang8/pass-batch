package com.marathon.passbatch.repository;

import java.util.Map;
import java.util.Optional;

import com.marathon.passbatch.config.TestBatchConfig;
import com.marathon.passbatch.repository.user.UserEntity;
import com.marathon.passbatch.repository.user.UserRepository;
import com.marathon.passbatch.repository.user.UserStatus;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@DataJpaTest
@ActiveProfiles("test")
@ContextConfiguration(classes = {TestBatchConfig.class}) // BaseEntity의 생성일시, 수정일시 때문
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void test_save() {
        // given
        UserEntity userEntity = new UserEntity();
        final String userId = "C100" + RandomStringUtils.randomNumeric(4);
        userEntity.setUserId(userId);
        userEntity.setUserName("Jake");
        userEntity.setUserStatus(UserStatus.ACTIVATE);
        userEntity.setPhone("01012341234");
        //userEntity.setMeta(Map.of("uuid", "abcd1234"));

        // when
        userRepository.save(userEntity);


        // then
        final Optional<UserEntity> optionalUserEntity = userRepository.findById(userId);
        assert(optionalUserEntity.isPresent());



    }



}
