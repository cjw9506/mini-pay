package com.minipay.daliyLimit.repository;

import com.minipay.daliyLimit.domain.DailyLimit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyLimitRepository extends JpaRepository<DailyLimit, Long> {
}
