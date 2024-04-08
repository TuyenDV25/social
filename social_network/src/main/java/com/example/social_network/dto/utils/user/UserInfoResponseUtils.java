package com.example.social_network.dto.utils.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.social_network.dto.user.UserInforResDto;
import com.example.social_network.dto.utils.ResponseUtilsAdapter;
import com.example.social_network.entity.UserInfo;
import com.example.social_network.mapper.image.ImageResponseMapper;
import com.example.social_network.mapper.user.UserMapper;
import com.example.social_network.utils.Utils;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UserInfoResponseUtils extends ResponseUtilsAdapter<UserInfo, UserInforResDto> {
	private final UserMapper userMapper;
	private final ImageResponseMapper imageMapper;

	@Override
	public UserInforResDto convert(UserInfo obj) {
		UserInforResDto userInfoResponse = userMapper.entityToDto(obj);
		userInfoResponse.setAvatar(imageMapper.entityToDto(Utils.getLast(obj.getAvatarImage())));
		return userInfoResponse;
	}

	@Override
	public List<UserInforResDto> convert(List<UserInfo> obj) {
		List<UserInforResDto> userInfoResponseList = new ArrayList<>();
		obj.stream().forEach(o -> {
			UserInforResDto userInfoResponse = userMapper.entityToDto(o);
			userInfoResponse.setAvatar(imageMapper.entityToDto(Utils.getLast(o.getAvatarImage())));
			userInfoResponseList.add(userInfoResponse);
		});
		return userInfoResponseList;
	}
}
