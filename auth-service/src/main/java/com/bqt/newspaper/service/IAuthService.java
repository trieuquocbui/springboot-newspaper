package com.bqt.newspaper.service;


import com.bqt.newspaper.payload.AccountResponse;
import com.bqt.newspaper.payload.LoginRequest;
import com.bqt.newspaper.payload.RegisterRequest;
import com.bqt.newspaper.payload.TokenResponse;

public interface IAuthService {
    TokenResponse createToken(LoginRequest loginRequest);

    AccountResponse createAccount(RegisterRequest registerRequest);
}
