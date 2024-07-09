package com.minipay.account.dto;

import com.minipay.account.domain.Account;
import com.minipay.account.domain.Type;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetAccountResponseDTO {

    private Long id;
    private long balance;
    private Type type;


    public GetAccountResponseDTO(Account account) {
        this.id = account.getId();
        this.balance = account.getBalance();
        this.type = account.getType();
    }

    @Builder
    public GetAccountResponseDTO(Long id, long balance, Type type) {
        this.id = id;
        this.balance = balance;
        this.type = type;
    }




}
