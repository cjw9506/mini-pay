package com.minipay.transaction.repository;

import com.minipay.account.domain.Account;
import com.minipay.transaction.domain.QTransaction;
import com.minipay.transaction.domain.Transaction;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;


import java.util.List;

@RequiredArgsConstructor
public class TransactionRepositoryImpl implements TransactionRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Transaction> getList(Account account, Long transactionId, int size) {
        QTransaction transaction = QTransaction.transaction;
        BooleanExpression predicate = transaction.receiverAccount.eq(account)
                .or(transaction.senderAccount.eq(account));
        BooleanBuilder dynamicLtId = new BooleanBuilder();
        if (transactionId != null) {
            dynamicLtId.and(transaction.id.lt(transactionId));
        }

        return jpaQueryFactory.selectFrom(transaction)
                .where(predicate.and(dynamicLtId))
                .orderBy(transaction.id.desc())
                .limit(size)
                .fetch();
    }

    @Override
    public List<Transaction> getList(Long accountId, int page, int size) {
//        QTransaction transaction = QTransaction.transaction;
//        BooleanExpression predicate = transaction.receiverAccount.eq(account)
//                .or(transaction.senderAccount.eq(account));
//
//        return jpaQueryFactory.selectFrom(transaction)
//                .where(predicate)
//                .offset((long) (page - 1) * size)
//                .limit(size)
//                .orderBy(transaction.id.desc())
//                .fetch();
        String sql = "SELECT * " +
                "FROM transaction " +
                "WHERE receiver_account_id = :receiverId " +
                "UNION " +
                "SELECT * " +
                "FROM transaction " +
                "WHERE sender_account_id = :senderId " +
                "ORDER BY id DESC " +
                "LIMIT :limit OFFSET :offset";
        Query query = entityManager.createNativeQuery(sql, Transaction.class);
        query.setParameter("receiverId", accountId);
        query.setParameter("senderId", accountId);
        query.setParameter("limit", size);
        query.setParameter("offset", (long) (page - 1) * size);
        return query.getResultList();
    }

    @Override
    public List<Transaction> getList(Account account, int page, int size) {
        QTransaction transaction = QTransaction.transaction;
        BooleanExpression predicate = transaction.receiverAccount.eq(account)
                .or(transaction.senderAccount.eq(account));

        return jpaQueryFactory.selectFrom(transaction)
                .where(predicate)
                .offset((long) (page - 1) * size)
                .limit(size)
                .orderBy(transaction.id.desc())
                .fetch();
    }


}