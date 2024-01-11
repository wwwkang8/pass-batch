package com.marathon.passbatch.repository.packaze;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface PackageRepository extends JpaRepository<PackageEntity, Integer> {

    List<PackageEntity> findByCreatedAtAfter(LocalDateTime dateTime, Pageable packageSeq);

    @Transactional
    @Modifying
    @Query(value = "UPDATE PackageEntity p" +
        "           SET p.count = :count, " +
        "               p.period = :period" +
        "           WHERE p.packageSeq = :packageSeq")
    int updateCountAndPeriod(Integer packageSeq, Integer count, Integer period);
}
