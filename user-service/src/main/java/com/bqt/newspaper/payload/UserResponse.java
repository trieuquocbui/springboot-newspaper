package com.bqt.newspaper.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Builder
@Data
@AllArgsConstructor
public class UserResponse {
    private String username;
    private String password;
    private String role;
}
