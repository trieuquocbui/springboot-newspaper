package com.bqt.newspaper.controller;

import com.bqt.newspaper.constant.PaginationConstant;
import com.bqt.newspaper.exception.GlobalCode;
import com.bqt.newspaper.payload.*;
import com.bqt.newspaper.service.IOriginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/origin/")
@RequiredArgsConstructor
public class OriginController {
    private final IOriginService originService;

    @GetMapping("list")
    public ResponseEntity<ApiResponse<PaginationResponse<OriginResponse>>> getOriginList(
            @RequestParam(name = "page", defaultValue = PaginationConstant.DEFAULT_PAGE) int page,
            @RequestParam(name = "limit", defaultValue = PaginationConstant.DEFAULT_LIMIT) int limit,
            @RequestParam(name = "sortDir", defaultValue = PaginationConstant.DEFAULT_DIR) String sortDir,
            @RequestParam(name = "sortBy", defaultValue = PaginationConstant.DEFAULT_ID) String sortBy,
            @RequestParam(name = "search", required = false) String search
    ){
        InfoPage infoPage = new InfoPage(page,limit,sortDir,sortBy,null,null,search,null);
        PaginationResponse<OriginResponse> response = originService.findByOriginList(infoPage);
        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS,"Lấy danh sách nguồn bài báo thành công",response);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("all")
    public ResponseEntity<ApiResponse<List<OriginResponse>>> getAllOrigin(){
        List<OriginResponse> originResponses = originService.getAllOrigin();
        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS,"Lấy danh sách nguồn bài báo thành công",originResponses);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }


    @GetMapping("{originId}")
    public ResponseEntity<ApiResponse<OriginResponse>> getOrigin(@PathVariable(name = "originId") String originId){
        OriginResponse originResponse = originService.findOrigin(originId);
        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS,"Lấy nguồn bài báo thành công",originResponse);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PostMapping("add")
    public ResponseEntity<ApiResponse<OriginResponse>> createOrigin(@RequestBody OriginRequest originRequest){
        OriginResponse originResponse = originService.createOrigin(originRequest);
        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS,"Thêm nguồn bài báo thành công",originResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @PutMapping("edit/{originId}")
    public ResponseEntity<ApiResponse<OriginResponse>> editOrigin(@PathVariable(name = "originId") String originId,
                                                     @RequestBody OriginRequest originRequest){
        OriginResponse originResponse = originService.editOrigin(originId,originRequest);
        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS,"Chỉnh sửa nguồn bài báo thành công",originResponse);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @DeleteMapping("delete/{originId}")
    public ResponseEntity<ApiResponse<String>> deleteOrigin(@PathVariable(name = "originId") String originId){
        originService.deleteOrigin(originId);
        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS,"Xoá nguồn bài báo thành công ",originId);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
