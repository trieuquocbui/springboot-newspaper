package com.bqt.newspaper.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationRequest {
    private String image;

    private String content;

    private String newspaperId;

    private Date dateTime;

}
