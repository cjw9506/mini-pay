package com.minipay.deposit.domain;

import com.minipay.account.domain.Account;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Deposit {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    private long amount;

    private LocalDateTime timeStamp;

    @Builder
    public Deposit(Account account, long amount, LocalDateTime timeStamp) {
        this.account = account;
        this.amount = amount;
        this.timeStamp = timeStamp;
    }
}
