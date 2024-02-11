package com.marathon.passbatch.repository.pass;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "bulk_pass")
public class BulkPassEntity_T {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bulkPassSeq;
    private Integer packageSeq;
    private String  userGroupId;

    @Enumerated(EnumType.STRING)
    private BulkPassStatus_T status;
    private Integer count;

    private LocalDateTime startedAt;
    private LocalDateTime endedAt;





}
