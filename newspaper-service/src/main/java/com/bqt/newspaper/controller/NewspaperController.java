package com.bqt.newspaper.controller;

import com.bqt.newspaper.constant.PaginationConstant;
import com.bqt.newspaper.exception.GlobalCode;
import com.bqt.newspaper.payload.*;
import com.bqt.newspaper.service.INewspaperService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/newspaper/")
@RequiredArgsConstructor
public class NewspaperController {

    private final INewspaperService newspaperService;

    @GetMapping("list/all-new-newspaper")
    public ResponseEntity<ApiResponse<List<NewspaperResponse>>> getAllNewNewspaper(){
        List<NewspaperResponse> list = newspaperService.getAllNewNewspaper();
        ApiResponse<List<NewspaperResponse>> response = new ApiResponse<>(GlobalCode.SUCCESS,"Lấy bài báo mới nhất thành công",list);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("list/rand")
    public ResponseEntity<ApiResponse<List<NewspaperResponse>>> getRandNewNewspaper(@RequestParam(name = "limit", defaultValue = PaginationConstant.DEFAULT_LIMIT) int limit){
        List<NewspaperResponse> list = newspaperService.getRandNewNewspaper(limit);
        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS,"Lấy danh sách bài báo thành công",list);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("list")
    public ResponseEntity<ApiResponse<PaginationResponse<NewspaperResponse>>> getNewspaperList(
            @RequestParam(name = "page", defaultValue = PaginationConstant.DEFAULT_PAGE) int page,
            @RequestParam(name = "limit", defaultValue = PaginationConstant.DEFAULT_LIMIT) int limit,
            @RequestParam(name = "sortDir", defaultValue = PaginationConstant.DEFAULT_DIR) String sortDir,
            @RequestParam(name = "sortBy", defaultValue = PaginationConstant.DEFAULT_ID) String sortBy,
            @RequestParam(name = "topic", defaultValue = PaginationConstant.DEFAULT_ALL) String topic,
            @RequestParam(name = "origin", defaultValue = PaginationConstant.DEFAULT_ALL) String origin,
            @RequestParam(name = "search", required = false) String search,
            @RequestParam(name = "date", required = false)  String date
    ) throws ParseException {
        PaginationResponse<NewspaperResponse> response;
        if(date != null){
            InfoPage infoPage = new InfoPage(page,limit,sortDir,sortBy,topic,origin,null,date);
            response = newspaperService.findByNewspaperListByDate(infoPage);
        } else{
            InfoPage infoPage = new InfoPage(page,limit,sortDir,sortBy,topic,origin,search,null);
            response = newspaperService.findByNewspaperList(infoPage);
        }
        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS,"Lấy danh sách bài báo thành công",response);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("{newspaperId}")
    public ResponseEntity<ApiResponse<NewspaperResponse>> getNewspaper(@PathVariable(name = "newspaperId") String newspaperId){
        NewspaperResponse newspaperResponse = newspaperService.findNewspaper(newspaperId);
        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS,"Lấy bài báo thành công",newspaperResponse);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PostMapping("add")
    public ResponseEntity<ApiResponse<NewspaperResponse>> createNewspaper(
            @RequestPart(name = "files",required = false) MultipartFile[] files,
            @RequestPart(name = "data") NewspaperRequest newspaperRequest){
        NewspaperResponse newspaperResponse = newspaperService.createNewspaper(files,newspaperRequest);
        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS,"Tạo bài báo thành công",newspaperResponse);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PutMapping("edit/{newspaperId}")
    public ResponseEntity<ApiResponse<NewspaperResponse>> editNewspaper(
            @PathVariable(name = "newspaperId") String newspaperId,
            @RequestPart(name = "files",required = false) MultipartFile[] files,
            @RequestPart(name = "data") NewspaperRequest newspaperRequest){
        NewspaperResponse newspaperResponse = newspaperService.editNewspaper(newspaperId,newspaperRequest,files);
        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS,"Chỉnh sữa bài báo thành công",newspaperResponse);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @DeleteMapping("delete/{newspaperId}")
    public ResponseEntity<ApiResponse<String>> deleteNewspaper(@PathVariable(name = "newspaperId") String newspaperId){
        newspaperService.deleteNewspaper(newspaperId);
        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS,"Xoá thành công bài báo",newspaperId);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
