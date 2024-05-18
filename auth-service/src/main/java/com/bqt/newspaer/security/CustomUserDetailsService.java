package com.bqt.newspaer.security;

import com.bqt.newspaer.client.UserClient;
import com.bqt.newspaer.exception.GlobalCode;
import com.bqt.newspaer.payload.ApiResponse;
import com.bqt.newspaer.payload.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserClient userClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        ApiResponse<User> apiResponse = userClient.getUser(username).getBody();
        User user = new User();
        if(apiResponse.getCode().equals(GlobalCode.SUCCESS)){
            user = apiResponse.getData();
        }

        return new CustomUserDetails(user);
    }
}
