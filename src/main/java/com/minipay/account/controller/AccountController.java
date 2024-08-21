package com.minipay.account.controller;

import com.minipay.account.domain.Type;
import com.minipay.account.dto.*;
import com.minipay.account.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Account", description = "Account API")
@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;


    @Operation(summary = "메인계좌 충전", description = "유저의 메인계좌에 돈을 충전합니다.")
    @ApiResponse(responseCode = "200", description = "성공")
    @ApiResponse(responseCode = "500", description = "일일 충전 한도 초과")
    @PostMapping // 입금
    public ResponseEntity<?> deposit(@RequestBody DepositDTO request) {

        accountService.deposit(request);

        return ResponseEntity.ok(null);
    }

    @Operation(summary = "적금계좌 생성", description = "유저의 적금계좌를 추가로 생성합니다.")
    @ApiResponse(responseCode = "200", description = "성공")
    @ApiResponse(responseCode = "500", description = "해당 회원을 찾을 수 없습니다.")
    @PostMapping("/saving") //적금계좌생성
    public ResponseEntity<?> addSavingAccount(@RequestBody AccountDTO request) {

        accountService.addAccount(request);

        return ResponseEntity.ok(null);
    }

    @Operation(summary = "메인계좌에서 적금계좌로 이체", description = "유저의 메인계좌에서 적금계좌로 일정 금액을 송금합니다.")
    @ApiResponse(responseCode = "200", description = "성공")
    @ApiResponse(responseCode = "500", description = "계좌를 찾을 수 없거나, 메인계좌의 잔액이 부족한 경우 발생합니다.")
    @PatchMapping("/withdrawal") //이체 메인 -> 적금
    public ResponseEntity<?> withdrawal(@RequestBody WithdrawalDTO request) {

        accountService.withdrawal(request);

        return ResponseEntity.ok().body(null);
    }

    @Operation(summary = "해당 유저의 모든 계좌를 조회", description = "해당 유저의 메인계좌 및 적금계좌를 전부 불러옵니다.")
    @ApiResponse(responseCode = "200", description = "성공")
    @GetMapping("/{userId}")
    public ResponseEntity<?> getAccounts(@PathVariable("userId") Long userId) {
        List<GetAccountResponseDTO> accounts = accountService.getAccounts(userId);

        return ResponseEntity.status(HttpStatus.OK).body(accounts);
    }

    @Operation(summary = "해당 유저의 특정 계좌를 조회", description = "해다 유저의 여러 계좌 중 특정 계좌를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "성공")
    @GetMapping("/{userId}/account")
    public ResponseEntity<?> getAccount(@PathVariable("userId") Long userId,
                                        @RequestParam("type") Type type) {
        GetAccountResponseDTO account = accountService.getAccount(userId, type);

        return ResponseEntity.status(HttpStatus.OK).body(account);
    }

    @PostMapping("/remittance")
    public ResponseEntity<?> remittance(@RequestBody RemittanceDTO request) {

        accountService.remittance(request);

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/remittance/{userId}")
    public ResponseEntity<?> remittanceHistory(@PathVariable("userId") Long userId,
                                               //@RequestParam(required = false) Long lastTransactionId,
                                               @RequestParam("page") int page,
                                               @RequestParam("size") int size) {
        //List<TransactionsDTO> transactions = accountService.getRemittanceHistory(userId, lastTransactionId, size);
        List<TransactionsDTO> transactions = accountService.getRemittanceHistory(userId, page, size);
        return ResponseEntity.status(HttpStatus.OK).body(transactions);
    }

}
