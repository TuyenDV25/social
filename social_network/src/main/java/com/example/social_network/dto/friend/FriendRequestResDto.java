package com.example.social_network.dto.friend;

import com.example.social_network.dto.AbstractDto;
import com.example.social_network.dto.user.UserInforResDto;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FriendRequestResDto extends AbstractDto {
	private UserInforResDto userInfo;

	public UserInforResDto getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInforResDto userInfo) {
		this.userInfo = userInfo;
	}
}
