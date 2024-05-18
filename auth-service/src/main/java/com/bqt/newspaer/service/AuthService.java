package com.bqt.newspaer.service;

import com.bqt.newspaer.client.UserClient;
import com.bqt.newspaer.exception.GlobalCode;
import com.bqt.newspaer.payload.*;
import com.bqt.newspaer.security.jwt.JwtHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService{

    private final UserClient userClient;

    private final JwtHelper jwtHelper;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder encoder;

    @Override
    public TokenResponse createToken(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        String jwt = jwtHelper.generateJwtToken(authentication);

        return new TokenResponse(jwt);
    }

    @Override
    public AccountResponse createAccount(RegisterRequest registerRequest) {
        registerRequest.setPassword(encoder.encode(registerRequest.getPassword()));
        ApiResponse<AccountResponse> response = userClient.registerAccount(registerRequest).getBody();

        AccountResponse accountResponse = new AccountResponse();
        if(response.getCode().equals(GlobalCode.SUCCESS)){
            accountResponse = response.getData();
        }

        return accountResponse;
    }
}
