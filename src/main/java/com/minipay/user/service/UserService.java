package com.minipay.user.service;

import com.minipay.account.domain.Account;
import com.minipay.account.domain.Type;
import com.minipay.account.repository.AccountRepository;
import com.minipay.daliyLimit.domain.DailyLimit;
import com.minipay.daliyLimit.repository.DailyLimitRepository;
import com.minipay.user.domain.User;
import com.minipay.user.dto.SignupDTO;
import com.minipay.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Getter
public class UserService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final DailyLimitRepository dailyLimitRepository;

    @Transactional // 회원가입
    public void save(SignupDTO request) {

        User user = User.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .build();

        userRepository.save(user);

        //메인계좌 생성
        Account account = Account.builder()
                .user(user)
                .balance(0L)
                .type(Type.MAIN)
                .build();

        accountRepository.save(account);

        DailyLimit dailyLimit = DailyLimit.builder()
                .userId(user.getId())
                .dailyTotalBalance(0L)
                .build();

        dailyLimitRepository.save(dailyLimit);
    }
}
