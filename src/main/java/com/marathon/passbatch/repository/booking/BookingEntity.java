package com.marathon.passbatch.repository.booking;

import java.time.LocalDateTime;

import com.marathon.passbatch.repository.BaseEntity;
import com.marathon.passbatch.repository.pass.PassEntity;
import com.marathon.passbatch.repository.user.UserEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Entity
@Table(name="booking")
public class BookingEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookingSeq;
    private Integer passSeq;
    private String userId;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;
    private boolean usedPass;
    private boolean attended;

    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
    private LocalDateTime canceledAt;


    /**
     * @ManyToOne : 현재 클래스(BookingEntity)가 UserEntity와 N:1 관계를 맺는다
     * @JoinColumn : 조인 컬럼에 대한 맵핑을 지정한다. name은 외래키를 필드를 설정하는 것.
     * */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userId", insertable = false, updatable = false )
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "passSeq", insertable = false, updatable = false)
    private PassEntity passEntity;

    // endedAt
    public LocalDateTime getStatisticsAt() {
        return this.endedAt.withHour(0).withMinute(0).withSecond(0).withNano(0);
    }



}
