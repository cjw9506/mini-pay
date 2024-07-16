package com.minipay.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupDTO {

    private String username;
    private String password;

    @Builder
    public SignupDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
