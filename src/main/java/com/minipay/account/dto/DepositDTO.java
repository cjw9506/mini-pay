package com.minipay.account.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DepositDTO {

    private Long accountId;
    private long balance;

    @Builder
    public DepositDTO(Long accountId, long balance) {
        this.accountId = accountId;
        this.balance = balance;
    }
}
