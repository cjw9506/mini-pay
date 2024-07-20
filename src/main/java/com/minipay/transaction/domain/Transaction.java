package com.minipay.transaction.domain;

import com.minipay.account.domain.Account;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Transaction {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_account_id")
    private Account senderAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_account_id")
    private Account receiverAccount;

    private long amount;

    private String sourceOrDestination;

    private LocalDateTime timeStamp;

    @Builder
    public Transaction(Account senderAccount, Account receiverAccount,
                       long amount, String sourceOrDestination, LocalDateTime timeStamp) {
        this.senderAccount = senderAccount;
        this.receiverAccount = receiverAccount;
        this.amount = amount;
        this.sourceOrDestination = sourceOrDestination;
        this.timeStamp = timeStamp;
    }
}
