package com.minipay.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupDTO {

    private String username;
    private String password;

    public SignupDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
