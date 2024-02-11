package com.marathon.passbatch.repository.pass;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BulkPassRepository_T extends JpaRepository<BulkPassEntity_T, Integer> {

    List<BulkPassEntity_T> findByBulkPassStatusAndStartedAtGreaterThan(BulkPassStatus_T status, LocalDateTime startedAt);

}
