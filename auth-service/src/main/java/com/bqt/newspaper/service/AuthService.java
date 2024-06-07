package com.bqt.newspaper.service;

import com.bqt.newspaper.client.UserClient;
import com.bqt.newspaper.exception.GlobalCode;
import com.bqt.newspaper.payload.*;
import com.bqt.newspaper.security.jwt.JwtHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
