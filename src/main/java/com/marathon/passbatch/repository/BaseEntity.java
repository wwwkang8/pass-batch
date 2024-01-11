package com.marathon.passbatch.repository;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass /** 공통 객체 속성들을 모아서, 상속시켜서 공용으로 사용가능 */
@EntityListeners(AuditingEntityListener.class) //Auditing 정보를 캡처하는 Listner.
public abstract class BaseEntity {

    @CreatedDate // 생성일시를 생성.
    @Column(updatable = false, nullable = false) //update, null 불가하도록 설정
    private LocalDateTime createdAt;

    @LastModifiedDate // 마지막 수정일시를 생성합니다.
    private LocalDateTime modifiedAt;

}
