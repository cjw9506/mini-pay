package com.minipay.account.dto;

import com.minipay.account.domain.Account;
import com.minipay.transaction.domain.Transaction;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class TransactionsDTO {

    private Long receiverId;
    private Long senderId;
    private LocalDateTime timeStamp;
    private String sourceOrDestination;
    private Long amount;

    @Builder
    public TransactionsDTO(Long receiverId, Long senderId, LocalDateTime timeStamp,
                           String sourceOrDestination, Long amount) {
        this.receiverId = receiverId;
        this.senderId = senderId;
        this.timeStamp = timeStamp;
        this.sourceOrDestination = sourceOrDestination;
        this.amount = amount;
    }

    public TransactionsDTO(Transaction transaction) {
        this.receiverId = transaction.getReceiverAccount().getId();
        this.senderId = transaction.getSenderAccount().getId();
        this.timeStamp = transaction.getTimeStamp();
        this.sourceOrDestination = transaction.getSourceOrDestination();
        this.amount = transaction.getAmount();
    }


}
