package com.minipay.account.dto;

import com.minipay.account.domain.Type;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AccountDTO {

    private Long userId;
    private Type type;

    public AccountDTO(Long userId, Type type) {
        this.userId = userId;
        this.type = type;
    }
}
