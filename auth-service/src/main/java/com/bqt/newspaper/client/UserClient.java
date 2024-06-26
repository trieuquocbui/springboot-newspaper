package com.bqt.newspaper.client;

import com.bqt.newspaper.configuration.CustomFeignClientConfiguration;
import com.bqt.newspaper.payload.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "identity", url = "${USER_SERVICE_URL}", configuration = CustomFeignClientConfiguration.class)
public interface UserClient {

    @GetMapping("{username}")
    ResponseEntity<ApiResponse<User>> getUser(@PathVariable("username") String username);

    @PostMapping("register")
    ResponseEntity<ApiResponse<AccountResponse>> registerAccount(@RequestBody RegisterRequest registerRequest);
}
