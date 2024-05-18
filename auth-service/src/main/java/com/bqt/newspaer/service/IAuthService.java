package com.bqt.newspaer.service;


import com.bqt.newspaer.payload.AccountResponse;
import com.bqt.newspaer.payload.LoginRequest;
import com.bqt.newspaer.payload.RegisterRequest;
import com.bqt.newspaer.payload.TokenResponse;

public interface IAuthService {
    TokenResponse createToken(LoginRequest loginRequest);

    AccountResponse createAccount(RegisterRequest registerRequest);
}
