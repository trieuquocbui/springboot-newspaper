package com.bqt.newspaper.controller;


import com.bqt.newspaper.constant.PaginationConstant;
import com.bqt.newspaper.entity.Newspaper;
import com.bqt.newspaper.exception.GlobalCode;
import com.bqt.newspaper.payload.*;
import com.bqt.newspaper.service.IFavouriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/favourite/")
@RequiredArgsConstructor
public class FavouriteController {

    private final IFavouriteService favouriteService;

    @PostMapping("{username}/add/newspaper/{newspaperId}")
    public ResponseEntity<ApiResponse<FavouriteResponse>> saveFavouriteNewspaper(
            @PathVariable("username") String username,
            @PathVariable("newspaperId") String newspaperId,
            @RequestBody Newspaper newspaper){
        FavouriteResponse favouriteResponse = favouriteService.createFavouriteNewspaperForUser(username,newspaperId,newspaper);
        ApiResponse apiResponse = new ApiResponse<FavouriteResponse>(GlobalCode.SUCCESS,"Lưu thành công",favouriteResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @GetMapping("{username}")
    public ResponseEntity<ApiResponse<PaginationResponse<FavouriteResponse>>> saveFavouriteNewspaper(
            @RequestParam(name = "page", defaultValue = PaginationConstant.DEFAULT_PAGE) int page,
            @RequestParam(name = "limit", defaultValue = PaginationConstant.DEFAULT_LIMIT) int limit,
            @RequestParam(name = "sortDir", defaultValue = PaginationConstant.DEFAULT_DIR) String sortDir,
            @RequestParam(name = "sortBy", defaultValue = PaginationConstant.DEFAULT_ID) String sortBy,
            @PathVariable("username") String username){
        InfoPage infoPage = new InfoPage(page,limit,sortDir,sortBy);
        PaginationResponse<FavouriteResponse> paginationResponse = favouriteService.getFavouriteNewspaperForUser(username,infoPage);
        ApiResponse apiResponse = new ApiResponse<PaginationResponse<FavouriteResponse>>(GlobalCode.SUCCESS,"Lấy bài báo yêu thích thành công",paginationResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @DeleteMapping("{username}/delete/newspaper/{newspaperId}")
    public ResponseEntity<ApiResponse<FavouriteResponse>> deleteFavouriteNewspaper(
            @PathVariable("username") String username,
            @PathVariable("newspaperId") String newspaperId){
        String favouriteId = favouriteService.deleteFavouriteNewspaperForUser(username,newspaperId);
        ApiResponse apiResponse = new ApiResponse<String>(GlobalCode.SUCCESS,"Xoá thành công",favouriteId);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
