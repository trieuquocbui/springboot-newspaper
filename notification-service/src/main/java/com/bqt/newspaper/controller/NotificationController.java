package com.bqt.newspaper.controller;

import com.bqt.newspaper.constant.PaginationConstant;
import com.bqt.newspaper.exception.GlobalCode;
import com.bqt.newspaper.payload.ApiResponse;
import com.bqt.newspaper.payload.NotificationResponse;
import com.bqt.newspaper.payload.PaginationResponse;
import com.bqt.newspaper.service.INotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notification/")
@RequiredArgsConstructor
public class NotificationController {

    private final INotificationService notificationService;


    @GetMapping("list")
    public ResponseEntity<ApiResponse<PaginationResponse<NotificationResponse>>> getNotificationList(
            @RequestParam(name = "page", defaultValue = PaginationConstant.DEFAULT_PAGE) int page,
            @RequestParam(name = "limit", defaultValue = PaginationConstant.DEFAULT_LIMIT) int limit,
            @RequestParam(name = "sortDir", defaultValue = PaginationConstant.DEFAULT_DIR) String sortDir
    ){
        PaginationResponse<NotificationResponse> response = notificationService.getNotificationList(page,limit,sortDir);
        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS,"Lấy danh sách thông báo thành công",response);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
