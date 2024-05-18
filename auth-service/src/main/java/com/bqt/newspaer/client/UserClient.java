package com.bqt.newspaer.client;

import com.bqt.newspaer.configuration.CustomFeignClientConfiguration;
import com.bqt.newspaer.payload.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user", url = "http://localhost:9191/api/v1/user/", configuration = CustomFeignClientConfiguration.class)
public interface UserClient {

    @GetMapping("{username}")
    ResponseEntity<ApiResponse<User>> getUser(@PathVariable("username") String username);

    @PostMapping("register")
    ResponseEntity<ApiResponse<AccountResponse>> registerAccount(@RequestBody RegisterRequest registerRequest);
}
