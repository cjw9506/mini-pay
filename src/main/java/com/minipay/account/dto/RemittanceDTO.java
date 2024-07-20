package com.minipay.account.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RemittanceDTO {

    private Long senderAccountId;
    private Long receiverAccountId;
    private long balance;
    private String sourceOrDestination;

    public RemittanceDTO(Long senderAccountId, Long receiverAccountId,
                         long balance, String sourceOrDestination) {
        this.senderAccountId = senderAccountId;
        this.receiverAccountId = receiverAccountId;
        this.balance = balance;
        this.sourceOrDestination = sourceOrDestination;
    }
}
