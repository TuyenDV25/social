package com.example.social_network.service;

import com.example.social_network.dto.user.UserInfoPutReqDto;
import com.example.social_network.dto.user.UserInfoPutResDto;
import com.example.social_network.dto.user.UserInforReqDto;
import com.example.social_network.dto.user.UserInforResDto;

public interface UserService {

	UserInfoPutResDto updateInfo(UserInfoPutReqDto reqDto);

	UserInforResDto getUserInformation();

}
