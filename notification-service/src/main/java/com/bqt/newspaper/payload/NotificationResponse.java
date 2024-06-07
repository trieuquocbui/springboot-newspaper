package com.bqt.newspaper.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class NotificationResponse {
    private  String id;

    private String newspaperId;

    private String content;

    private Date dateTime;

    private String image;
}
