package com.bqt.newspaer.controller;

import com.bqt.newspaer.exception.GlobalCode;
import com.bqt.newspaer.payload.*;
import com.bqt.newspaer.service.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth/")
@RequiredArgsConstructor
public class AuthServiceController {

    private final IAuthService authService;

    @PostMapping("register")
    public ResponseEntity<ApiResponse<AccountResponse>> registerAccount(@RequestBody RegisterRequest registerRequest){

        AccountResponse accountResponse = this.authService.createAccount(registerRequest);
        ApiResponse<AccountResponse> response = new ApiResponse<>(GlobalCode.SUCCESS,"Đăng kí thành công",accountResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("login")
    public ResponseEntity<ApiResponse<TokenResponse>> loginAccount(@RequestBody LoginRequest loginRequest){

        TokenResponse tokenResponse = authService.createToken(loginRequest);
        ApiResponse<TokenResponse> response = new ApiResponse<>(GlobalCode.SUCCESS,"Đăng nhập thành công",tokenResponse);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
