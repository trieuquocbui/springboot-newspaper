package com.bqt.newspaer.service.impl;

import com.bqt.newspaer.entity.Notification;
import com.bqt.newspaer.payload.NotificationRequest;
import com.bqt.newspaer.payload.NotificationResponse;
import com.bqt.newspaer.payload.PaginationResponse;
import com.bqt.newspaer.repository.NotificationRepository;
import com.bqt.newspaer.service.INotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService implements INotificationService {

    private final NotificationRepository notificationRepository;

    private NotificationResponse convertToNotificationResponse(Notification notification){
        return  new NotificationResponse(notification.getNotificationId(),notification.getNewspaperId(),notification.getContent(),notification.getDateTime(),notification.getImage());
    }

    @Override
    public NotificationResponse createNotification(NotificationRequest notificationRequest) {
        Notification notification = new Notification();
        notification.setContent(notificationRequest.getContent());
        notification.setDateTime(notificationRequest.getDateTime());
        notification.setNewspaperId(notificationRequest.getNewspaperId());
        notification.setImage(notificationRequest.getImage());
        notification = notificationRepository.save(notification);
        return convertToNotificationResponse(notification);
    }

    @Override
    public PaginationResponse<NotificationResponse> getNotificationList(int page, int limit, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by("dateTime").ascending() : Sort.by("dateTime").descending();

        Pageable pageable = PageRequest.of(page - 1, limit,sort);

        Page<Notification> resultPage = notificationRepository.findAll(pageable);

        PaginationResponse<NotificationResponse> paginationResponse = new PaginationResponse<>();

        List<NotificationResponse> collect = resultPage.getContent().stream().map(
                notification -> convertToNotificationResponse(notification)).collect(Collectors.toList());

        paginationResponse.setContent(collect);
        paginationResponse.setLastPage(resultPage.isLast());
        paginationResponse.setTotalElements(resultPage.getTotalElements());
        paginationResponse.setPageSize(resultPage.getSize());
        paginationResponse.setPageNumber(resultPage.getNumber());
        paginationResponse.setTotalPages(resultPage.getTotalPages());

        return paginationResponse;
    }
}