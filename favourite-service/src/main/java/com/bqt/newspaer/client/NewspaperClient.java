package com.bqt.newspaer.client;

import com.bqt.newspaer.configuration.CustomFeignClientConfiguration;


import com.bqt.newspaer.entity.Newspaper;
import com.bqt.newspaer.payload.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name = "newspaper", url = "http://api-gateway:9191/api/v1/newspaper/", configuration = CustomFeignClientConfiguration.class)
@FeignClient(name = "newspaper", url = "http://localhost:9191/api/v1/newspaper/", configuration = CustomFeignClientConfiguration.class)
public interface NewspaperClient {

    @GetMapping("{newspaperId}")
    ResponseEntity<ApiResponse<Newspaper>> getNewspaper(@PathVariable(name = "newspaperId") String newspaperId);
}
