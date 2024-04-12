package com.example.social_network.service;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.example.social_network.dto.user.UserInfoListPostReqDto;
import com.example.social_network.dto.user.UserInfoPutReqDto;
import com.example.social_network.dto.user.UserInforResDto;
import com.example.social_network.entity.UserInfo;

public interface UserService {

	UserInforResDto updateInfo(UserInfoPutReqDto reqDto, MultipartFile[] multipartFile);

	UserInforResDto getUserInformation();

	Page<UserInforResDto> searchUserByName(UserInfoListPostReqDto reqDto);

	void updateUserInfo(UserInfo userInfo);

	UserInfo findOneById(Long id);

	UserInforResDto findDetailUser(Long id);
}
