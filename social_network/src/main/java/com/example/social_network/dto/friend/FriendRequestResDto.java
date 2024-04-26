package com.example.social_network.dto.friend;

import java.util.Date;

import com.example.social_network.dto.user.UserInforResDto;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FriendRequestResDto {

	private Date createdDate;

	private UserInforResDto userInfo;

	public UserInforResDto getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInforResDto userInfo) {
		this.userInfo = userInfo;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

}
