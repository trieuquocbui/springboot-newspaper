package com.bqt.newspaper.payload;

import com.bqt.newspaper.entity.Newspaper;
import lombok.Data;

import java.util.Date;

@Data
public class FavouriteResponse {
    private String id;

    private String username;

    private Newspaper newspaper;

    private Date datetime;
}
