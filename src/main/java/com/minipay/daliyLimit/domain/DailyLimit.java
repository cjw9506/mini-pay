package com.minipay.daliyLimit.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class DailyLimit {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private long dailyTotalBalance;

    @Builder
    public DailyLimit(Long userId, long dailyTotalBalance) {
        this.userId = userId;
        this.dailyTotalBalance = dailyTotalBalance;
    }

    public void addBalance(long dailyTotalBalance) {
        this.dailyTotalBalance += dailyTotalBalance;
    }

    public void resetDailyBalance() {
        this.dailyTotalBalance = 0;
    }
}
