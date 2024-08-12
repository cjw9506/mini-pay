package com.minipay.batch.dto;

import com.minipay.account.domain.Account;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AccountProcessingResult {

    private Account mainAccount;
    private Account savingAccount;

    @Builder
    public AccountProcessingResult(Account mainAccount, Account savingAccount) {
        this.mainAccount = mainAccount;
        this.savingAccount = savingAccount;
    }
}
