package com.minipay.daliyLimit.repository;

import com.minipay.daliyLimit.domain.DailyLimit;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyLimitRepository extends JpaRepository<DailyLimit, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select d from DailyLimit d where d.id = :id")
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "5000")})
    DailyLimit findByIdWithPessimisticLock(@Param("id") Long id);
}
