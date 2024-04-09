package com.example.social_network.service;

import org.springframework.data.domain.Page;

import com.example.social_network.dto.user.UserInfoListPostReqDto;
import com.example.social_network.dto.user.UserInfoPutReqDto;
import com.example.social_network.dto.user.UserInfoPutResDto;
import com.example.social_network.dto.user.UserInforResDto;
import com.example.social_network.entity.UserInfo;

public interface UserService {

	UserInfoPutResDto updateInfo(UserInfoPutReqDto reqDto);

	UserInforResDto getUserInformation();

	Page<UserInforResDto> searchUserByName(UserInfoListPostReqDto reqDto);

	void updateUserInfo(UserInfo userInfo);
	
	UserInfo findOneById(Long id);
}
