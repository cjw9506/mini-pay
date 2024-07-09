package com.minipay.deposit.repository;

import com.minipay.deposit.domain.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DepositRepository extends JpaRepository<Deposit, Long> {

    @Query("SELECT d FROM Deposit d WHERE d.account.id = :accountId AND d.timeStamp = :date")
    List<Deposit> findDepositsForToday(@Param("accountId") Long accountId, @Param("date") LocalDate date);

}
