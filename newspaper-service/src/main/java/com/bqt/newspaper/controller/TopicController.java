package com.bqt.newspaper.controller;

import com.bqt.newspaper.constant.PaginationConstant;
import com.bqt.newspaper.exception.GlobalCode;
import com.bqt.newspaper.payload.*;
import com.bqt.newspaper.service.ITopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/topic/")
@RequiredArgsConstructor
public class TopicController {
    private final ITopicService topicService;

    @GetMapping("list")
    public ResponseEntity<ApiResponse<PaginationResponse<TopicResponse>>> getTopicList(
            @RequestParam(name = "page", defaultValue = PaginationConstant.DEFAULT_PAGE) int page,
            @RequestParam(name = "limit", defaultValue = PaginationConstant.DEFAULT_LIMIT) int limit,
            @RequestParam(name = "sortDir", defaultValue = PaginationConstant.DEFAULT_DIR) String sortDir,
            @RequestParam(name = "sortBy", defaultValue = PaginationConstant.DEFAULT_ID) String sortBy,
            @RequestParam(name = "search", required = false) String search
    ){
        InfoPage infoPage = new InfoPage(page,limit,sortDir,sortBy,search,null,search,null);
        PaginationResponse<TopicResponse> response = topicService.findByTopicList(infoPage);
        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS,"Lấy danh sách chủ đề thành công",response);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("all")
    public ResponseEntity<ApiResponse<List<TopicResponse>>> getTopicAll(){
        List<TopicResponse> topicResponses = topicService.getTopicAll();
        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS,"Lấy tất cả chủ đề thành công",topicResponses);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PostMapping("add")
    public ResponseEntity<ApiResponse<TopicResponse>> createTopic(@RequestBody TopicRequest topicRequest){
        TopicResponse topicResponse = topicService.createTopic(topicRequest);
        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS,"Thêm chủ đề thành công",topicResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @GetMapping("{topicId}")
    public ResponseEntity<ApiResponse<TopicResponse>> getTopic(@PathVariable(name = "topicId") String topicId){
        TopicResponse topicResponse = topicService.findTopic(topicId);
        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS,"Lấy chủ đề thành công",topicResponse);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PutMapping("edit/{topicId}")
    public ResponseEntity<ApiResponse<TopicResponse>> editTopic(
            @PathVariable(name = "topicId") String topicId,
            @RequestBody TopicRequest topicRequest){
        TopicResponse topicResponse = topicService.editTopic(topicId,topicRequest);
        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS,"Chỉnh sữa chủ đề thành công",topicResponse);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @DeleteMapping("delete/{topicId}")
    public ResponseEntity<ApiResponse<String>> deleteTopic(@PathVariable(name = "topicId") String topicId){
        topicService.deleteTopic(topicId);
        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS,"Xoá chủ đề bài báo",topicId);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
