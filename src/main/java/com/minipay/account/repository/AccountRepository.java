package com.minipay.account.repository;

import com.minipay.account.domain.Account;
import com.minipay.account.domain.Type;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findAllByUserId(Long userId);

    Account findByUserIdAndType(Long userId, Type type);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select a from Account a where a.id = :id")
    Account findByIdWithPessimisticLock(@Param("id") Long id);

    @Query("SELECT a FROM Account a WHERE a.type = 'FREE_SAVING' or a.type = 'REGULAR_SAVING'")
    List<Account> findAllSavingsAccounts();


}
