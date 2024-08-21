package com.minipay.account.service;

import com.minipay.account.domain.Account;
import com.minipay.account.domain.Type;
import com.minipay.account.dto.DepositDTO;
import com.minipay.account.repository.AccountRepository;
import com.minipay.daliyLimit.domain.DailyLimit;
import com.minipay.daliyLimit.repository.DailyLimitRepository;
import com.minipay.deposit.repository.DepositRepository;
import com.minipay.user.domain.User;
import com.minipay.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;



import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private DepositRepository depositRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private DailyLimitRepository dailyLimitRepository;

    @Test
    @DisplayName("메인계좌에 충전 성공")
    void depositSuccess() {
        // Arrange
        User user = User.builder()
                .username("testUser")
                .password("testPassword")
                .build();

        Account account = Account.builder()
                .type(Type.MAIN)
                .user(user)
                .regularFee(0)
                .balance(0L)
                .build();

        DailyLimit dailyLimit = DailyLimit.builder()
                .userId(user.getId())
                .dailyTotalBalance(0L)
                .build();

        DepositDTO request = new DepositDTO(account.getId(), 10000L);

        given(accountRepository.findByIdWithPessimisticLock(account.getId()))
                .willReturn(account);

        given(dailyLimitRepository.findByIdWithPessimisticLock(user.getId()))
                .willReturn(dailyLimit);

        accountService.deposit(request);

        assertThat(account.getBalance()).isEqualTo(10000L);

    }
}