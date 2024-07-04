package com.minipay.account.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WithdrawalDTO {

    private Long mainAccountId;
    private Long savingAccountId;
    private Long balance;

    public WithdrawalDTO(Long mainAccountId, Long savingAccountId, Long balance) {
        this.mainAccountId = mainAccountId;
        this.savingAccountId = savingAccountId;
        this.balance = balance;
    }
}
