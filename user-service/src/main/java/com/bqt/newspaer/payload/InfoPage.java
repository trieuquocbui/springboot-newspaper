package com.bqt.newspaer.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InfoPage {
    private int page, limit;
    private String sortDir, sortBy;
    private String search;
}
