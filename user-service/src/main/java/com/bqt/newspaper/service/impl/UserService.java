package com.bqt.newspaper.service.impl;

import com.bqt.newspaper.client.FileClient;
import com.bqt.newspaper.entities.Role;
import com.bqt.newspaper.entities.User;
import com.bqt.newspaper.event.FileEvent;
import com.bqt.newspaper.exception.EntityAlreadyException;
import com.bqt.newspaper.exception.EntityNotFoundException;
import com.bqt.newspaper.exception.GlobalCode;
import com.bqt.newspaper.payload.*;
import com.bqt.newspaper.repository.RoleRepository;
import com.bqt.newspaper.repository.UserRepository;
import com.bqt.newspaper.service.IUserService;
import com.bqt.newspaper.specification.SearchCriteria;
import com.bqt.newspaper.specification.UserSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final FileClient fileClient;

    private final FileEvent fileEvent;
    protected AccountResponse convertToAccountResponse(User user){
        return new AccountResponse(user.getUsername());
    }

    protected UserResponse convertToUserResponse(User user){
        return new UserResponse(user.getUsername(), user.getPassword(),user.getRole().getName());
    }
    protected ProfileResponse convertToProfileResponse(User user){
        return new ProfileResponse(user.getUsername(), user.getFullName(),user.getThumbnail());
    }


    @Override
    public AccountResponse registerAccount(RegisterRequest registerRequest) {
        Role role = roleRepository.findById(registerRequest.getRole()).orElseThrow(
                () -> new EntityNotFoundException("Chức vụ không tồn tại", GlobalCode.ERROR_ENTITY_NOT_FOUND));

        boolean checkUsername = userRepository.existsByUsername(registerRequest.getUsername());

        if(checkUsername){
            throw new EntityAlreadyException("Tài khoản đã tồn tại",GlobalCode.ERROR_NAME_EXIST);
        }
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setFullName(registerRequest.getFullName());
        user.setPassword(registerRequest.getPassword());
        user.setRole(role);
        user = userRepository.save(user);

        return convertToAccountResponse(user);
    }

    @Override
    public PaginationResponse<ProfileResponse> getUserList(InfoPage infoPage) {
        Sort sort = infoPage.getSortDir().equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(infoPage.getSortBy()).ascending() : Sort.by(infoPage.getSortBy()).descending();

        Pageable pageable = PageRequest.of(infoPage.getPage() - 1, infoPage.getLimit(),sort);

        SearchCriteria searchCriteria = new SearchCriteria(infoPage.getSearch());

        UserSpecification userSpecification = new UserSpecification(searchCriteria);

        Page<User> resultPage = this.userRepository.findAll(userSpecification,pageable);

        PaginationResponse<ProfileResponse> paginationResponse = new PaginationResponse<>();

        List<ProfileResponse> collect = resultPage.getContent().stream().map(
                newspaper -> convertToProfileResponse(newspaper)).collect(Collectors.toList());

        paginationResponse.setContent(collect);
        paginationResponse.setLastPage(resultPage.isLast());
        paginationResponse.setTotalElements(resultPage.getTotalElements());
        paginationResponse.setPageSize(resultPage.getSize());
        paginationResponse.setPageNumber(resultPage.getNumber());
        paginationResponse.setTotalPages(resultPage.getTotalPages());

        return paginationResponse;
    }

    @Override
    public UserResponse getUser(String username) {
        User user = userRepository.findById(username).orElseThrow(
                () -> new EntityNotFoundException("Không tìm thấy tài khoản",GlobalCode.ERROR_ENTITY_NOT_FOUND));
        return convertToUserResponse(user);
    }

    @Override
    public ProfileResponse getProfile(String username) {
        User user = userRepository.findById(username).orElseThrow(
                () -> new EntityNotFoundException("Không tìm thấy tài khoản",GlobalCode.ERROR_ENTITY_NOT_FOUND));
        return convertToProfileResponse(user);
    }

    @Override
    public ProfileResponse editProfile(String username, String fullName, MultipartFile file) {
        User user = userRepository.findById(username).orElseThrow(
                () -> new EntityNotFoundException("Không tìm thấy tài khoản",GlobalCode.ERROR_ENTITY_NOT_FOUND));

        if(file != null){
            MultipartFile[] multipartFiles = new MultipartFile[1];
            multipartFiles[0] = file;
            String thumbnail = this.fileClient.uploadFiles(multipartFiles).getBody().getNames().get(0);

            if(!user.getThumbnail().equals("peaa3cc2f6-51c8-47bb-a1ad-21d8f3a939b1")){
                fileEvent.deleteFile(Arrays.asList(user.getThumbnail()));
            }
            user.setThumbnail(thumbnail);
        }
        user.setFullName(fullName);
        user = userRepository.save(user);
        return convertToProfileResponse(user);
    }
}
