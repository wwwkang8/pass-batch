package com.marathon.passbatch.repository.pass;

import java.time.LocalDateTime;

import com.marathon.passbatch.repository.BaseEntity;
import com.marathon.passbatch.repository.user.UserEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name="pass")
public class PassEntity extends BaseEntity {

    /**
     * @Entity : 데이터베이스와 객체간의 맵핑을 시작하는데 사용.
     * */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer passSeq;
    private String userId;
    private Integer pacakageSeq;

    @Enumerated(EnumType.STRING)
    private PassStatus passStatus;
    private Integer remainingCount;

    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
    private LocalDateTime expiredAt;

    @ManyToOne
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    private UserEntity userEntity;


}
