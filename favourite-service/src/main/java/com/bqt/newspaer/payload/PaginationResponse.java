package com.bqt.newspaer.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PaginationResponse<T> {
    private Collection<T> content;

    private int pageNumber;

    private int pageSize;

    private int totalPages;

    private long totalElements;

    private boolean isLastPage;
}
