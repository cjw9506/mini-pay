package com.minipay.account.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RemittanceDTO {

    private Long senderAccountId;
    private Long receiverAccountId;
    private long balance;

    public RemittanceDTO(Long senderAccountId, Long receiverAccountId, long balance) {
        this.senderAccountId = senderAccountId;
        this.receiverAccountId = receiverAccountId;
        this.balance = balance;
    }
}
