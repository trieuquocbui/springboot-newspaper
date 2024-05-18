package com.bqt.newspaer.configuration;

import com.bqt.newspaer.filter.JwtAdminAuthenticationFilter;
import com.bqt.newspaer.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {

    private final JwtAdminAuthenticationFilter jwtAdminAuthenticationFilter;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    // RouteLocator is an interface in spring cloud gateway to determine routes and filters for requests
    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder){
        return builder.routes()
                .route("newspaper-topic--service", r -> r.path(
                        "/api/v1/topic/add",
                                "/api/v1/topic/list",
                                "/api/v1/topic/edit/{topicId}",
                                "/api/v1/topic/delete/{topicId}").and()
                        .method(HttpMethod.GET,HttpMethod.POST,HttpMethod.DELETE,HttpMethod.PUT)
                        .filters(f -> f.filter(jwtAdminAuthenticationFilter))
                        .uri("lb://newspaper-service"))
                .route("newspaper-origin-service", r -> r.path(
                        "/api/v1/origin/add",
                                "/api/v1/origin/list",
                                "/api/v1/origin/edit/{originId}",
                                "/api/v1/origin/delete/{originId}").and()
                        .method(HttpMethod.GET,HttpMethod.POST,HttpMethod.DELETE,HttpMethod.PUT)
                        .filters(f -> f.filter(jwtAdminAuthenticationFilter))
                        .uri("lb://newspaper-service"))
                .route("newspaper-service", r -> r.path(
                                "/api/v1/newspaper/add",
                                "/api/v1/newspaper/edit/{newspaperId}",
                                "/api/v1/newspaper/delete/{newspaperId}").and()
                        .method(HttpMethod.GET,HttpMethod.POST,HttpMethod.DELETE,HttpMethod.PUT)
                        .filters(f -> f.filter(jwtAdminAuthenticationFilter))
                        .uri("lb://newspaper-service"))
                .route("auth-service", r -> r.path(
                        "/api/v1/auth/login",
                                "/api/v1/auth/register").and()
                        .method(HttpMethod.POST)
                        .uri("lb://auth-service"))
                .route("admin-user-service", r -> r.path(
                        "/api/v1/user/list"
                        ).and()
                        .method(HttpMethod.GET)
                        .filters(f -> f.filter(jwtAdminAuthenticationFilter))
                        .uri("lb://user-service"))
                .route("user-service", r -> r.path(
                                "/api/v1/user/edit/{username}"
                        ).and()
                        .method(HttpMethod.PUT)
                        .filters(f -> f.filter(jwtAuthenticationFilter))
                        .uri("lb://user-service"))
                .route("public-user-service", r -> r.path(
                                "/api/v1/user/{username}",
                                "/api/v1/user/profile/{username}",
                                "/api/v1/user/register").and()
                        .method(HttpMethod.POST,HttpMethod.GET)
                        .uri("lb://user-service"))
                .route("public-newspaper-service", r -> r.path(
                        "/api/v1/origin/all",
                                "/api/v1/origin/{originId}",
                                "/api/v1/newspaper/list",
                                "/api/v1/newspaper/list/rand",
                                "/api/v1/newspaper/{newspaperId}",
                                "/api/v1/newspaper/list/all-new-newspaper",
                                "/api/v1/topic/all",
                                "/websocket/(?.*)",
                                "/api/v1/topic/{topicId}").and()
                        .method(HttpMethod.GET)
                        .uri("lb://newspaper-service"))
                .route("file-service",r -> r.path("/api/v1/file/**")
                        .uri("lb://file-service"))
                .route("notification-service",r -> r.path("/api/v1/notification/list").and()
                        .method(HttpMethod.GET)
                        .uri("lb://notification-service"))
                .build();
    }
}
