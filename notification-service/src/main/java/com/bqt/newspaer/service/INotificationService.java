package com.bqt.newspaer.service;

import com.bqt.newspaer.payload.NotificationRequest;
import com.bqt.newspaer.payload.NotificationResponse;
import com.bqt.newspaer.payload.PaginationResponse;

public interface INotificationService {
    NotificationResponse createNotification(NotificationRequest notificationRequest);

    PaginationResponse<NotificationResponse> getNotificationList(int page, int limit, String sortDir);
}
