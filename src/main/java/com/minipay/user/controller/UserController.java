package com.minipay.user.controller;

import com.minipay.user.dto.SignupDTO;
import com.minipay.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User", description = "User API")
@RestController
@Getter
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원가입", description = "유저의 회원가입을 진행합니다.")
    @ApiResponse(responseCode = "200", description = "성공")
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupDTO request) {
        userService.save(request);

        return ResponseEntity.ok(null);
    }
}
