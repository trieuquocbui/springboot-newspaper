package com.bqt.newspaper.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountResponse {
    private String username;
    private String fullName;
}
