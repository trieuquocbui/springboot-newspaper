package com.bqt.newspaer.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ProfileResponse {
    private String username;
    private String fullName;
    private String thumbnail;
}
