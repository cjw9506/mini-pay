package com.minipay.account.domain;

import com.minipay.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private long balance;

    @Enumerated(value = EnumType.STRING)
    private Type type;

    private long regularFee;

    @Builder
    public Account(User user, long balance, Type type, long regularFee) {
        this.user = user;
        this.balance = balance;
        this.type = type;
        this.regularFee = regularFee;
    }

    public void deposit(long balance) {
        this.balance += balance;
    }

    public void addInterest(long balance) {
        this.balance += balance;
    }
}
