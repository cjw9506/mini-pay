package com.minipay.account.dto;

import com.minipay.account.domain.Type;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AccountDTO {

    private Long userId;
    private Type type;
    private long regularFee;

    @Builder
    public AccountDTO(Long userId, Type type, long regularFee) {
        this.userId = userId;
        this.type = type;
        this.regularFee = regularFee;
    }
}
