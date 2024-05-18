package com.bqt.newspaer.payload;

import lombok.Data;

import java.util.Date;

@Data
public class NotificationRequest {
    private String title;

    private String image;

    private String content;

    private Date dateTime;

    private String newspaperId;

}
