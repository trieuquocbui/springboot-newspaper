package com.bqt.newspaper.controller;

import com.bqt.newspaper.constant.PaginationConstant;
import com.bqt.newspaper.exception.GlobalCode;
import com.bqt.newspaper.payload.*;
import com.bqt.newspaper.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user/")
public class UserController {

    private final IUserService userService;

    @GetMapping("list")
    public ResponseEntity<ApiResponse<PaginationResponse<ProfileResponse>>> getUserList(
            @RequestParam(name = "page", defaultValue = PaginationConstant.DEFAULT_PAGE) int page,
            @RequestParam(name = "limit", defaultValue = PaginationConstant.DEFAULT_LIMIT) int limit,
            @RequestParam(name = "sortDir", defaultValue = PaginationConstant.DEFAULT_DIR) String sortDir,
            @RequestParam(name = "sortBy", defaultValue = PaginationConstant.USERNAME) String sortBy,
            @RequestParam(name = "search", required = false) String search
    ){
        InfoPage infoPage = new InfoPage(page,limit,sortDir,sortBy,search);
        PaginationResponse<ProfileResponse> response = userService.getUserList(infoPage);
        ApiResponse apiResponse = new ApiResponse<>(GlobalCode.SUCCESS,"Lấy danh sách tài khoản thành công",response);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse    );
    }

    @GetMapping("{username}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserToLogin(@PathVariable("username") String username){
        UserResponse user = userService.getUser(username);
        ApiResponse apiResponse = new ApiResponse<>(GlobalCode.SUCCESS,"Lấy thông tin thành công",user);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PostMapping("register")
    public ResponseEntity<ApiResponse<AccountResponse>> registerAccount(@RequestBody RegisterRequest registerRequest){
        AccountResponse response = userService.registerAccount(registerRequest);
        ApiResponse apiResponse = new ApiResponse<>(GlobalCode.SUCCESS,"Đăng kí thành công",response);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("profile/{username}")
    public ResponseEntity<ApiResponse<ProfileResponse>> getUser(@PathVariable(name = "username") String username){
        ProfileResponse profile = userService.getProfile(username);
        ApiResponse apiResponse = new ApiResponse<>(GlobalCode.SUCCESS,"Lấy profile thành công",profile);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PutMapping("edit/{username}")
    public ResponseEntity<ApiResponse<ProfileResponse>> editUser(
            @PathVariable(name = "username") String username,
            @RequestPart(name = "file",required = false)MultipartFile file,
            @RequestPart(name = "data") String fullName){
        ProfileResponse profile = userService.editProfile(username, fullName,file);
        ApiResponse apiResponse = new ApiResponse<>(GlobalCode.SUCCESS,"Cập nhật profile thành công",profile);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
