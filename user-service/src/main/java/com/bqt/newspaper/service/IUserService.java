package com.bqt.newspaper.service;

import com.bqt.newspaper.payload.*;
import org.springframework.web.multipart.MultipartFile;


public interface IUserService {

    AccountResponse registerAccount(RegisterRequest registerRequest);

    PaginationResponse<ProfileResponse> getUserList(InfoPage infoPage);

    UserResponse getUser(String username);

    ProfileResponse getProfile(String username);

    ProfileResponse editProfile(String username, String fullName, MultipartFile file);
}
