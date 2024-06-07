package com.bqt.newspaper.service;

import com.bqt.newspaper.payload.NotificationRequest;
import com.bqt.newspaper.payload.NotificationResponse;
import com.bqt.newspaper.payload.PaginationResponse;

public interface INotificationService {
    NotificationResponse createNotification(NotificationRequest notificationRequest);

    PaginationResponse<NotificationResponse> getNotificationList(int page, int limit, String sortDir);
    void deleteNotification(String newspaperId);
}
