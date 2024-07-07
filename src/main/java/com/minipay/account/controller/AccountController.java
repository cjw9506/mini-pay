package com.minipay.account.controller;

import com.minipay.account.dto.*;
import com.minipay.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping // 입금
    public ResponseEntity<?> deposit(@RequestBody DepositDTO request) {

        accountService.deposit(request);

        return ResponseEntity.ok(null);
    }

    @PostMapping("/saving") //적금계좌생성
    public ResponseEntity<?> addSavingAccount(@RequestBody AccountDTO request) {

        accountService.addAccount(request);

        return ResponseEntity.ok(null);
    }

    @PatchMapping("/withdrawal") //이체 메인 -> 적금
    public ResponseEntity<?> withdrawal(@RequestBody WithdrawalDTO request) {

        accountService.withdrawal1(request);

        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getAccounts(@PathVariable("userId") Long userId) {
        List<GetAccountResponseDTO> accounts = accountService.getAccounts(userId);

        return ResponseEntity.status(HttpStatus.OK).body(accounts);
    }


}
