package com.minipay.transaction.repository;

import com.minipay.account.domain.Account;
import com.minipay.transaction.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT t FROM Transaction t WHERE t.receiverAccount = :account OR t.senderAccount = :account")
    List<Transaction> findByReceiverOrSenderAccount(@Param("account") Account account);
}
