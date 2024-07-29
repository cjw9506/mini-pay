package com.minipay.transaction.repository;

import com.minipay.account.domain.Account;
import com.minipay.transaction.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends
        JpaRepository<Transaction, Long>, TransactionRepositoryCustom {

    @Query(value = "SELECT * FROM transaction WHERE receiver_account_id = :accountId " +
            "UNION ALL " +
            "SELECT * FROM transaction WHERE sender_account_id = :accountId",
            nativeQuery = true)
    List<Transaction> findByReceiverOrSenderAccount(@Param("account") Account account);
}
