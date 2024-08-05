package com.minipay.transaction.repository;

import com.minipay.account.domain.Account;
import com.minipay.transaction.domain.Transaction;

import java.util.List;

public interface TransactionRepositoryCustom {

    List<Transaction> getList(Account account, Long lastTransactionId, int size);

    List<Transaction> getList(Long id, int page, int size);

    List<Transaction> getList(Account account, int page, int size);
}