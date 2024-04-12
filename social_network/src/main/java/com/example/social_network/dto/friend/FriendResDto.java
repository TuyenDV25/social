package com.example.social_network.dto.friend;

import java.util.Date;

import com.example.social_network.dto.AbstractDto;
import com.example.social_network.dto.user.UserInforResDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FriendResDto extends AbstractDto {
	private UserInforResDto userInfo;

	private Long id;

	@JsonProperty("created_date")
	private Date createdDate;

	public UserInforResDto getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInforResDto userInfo) {
		this.userInfo = userInfo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
}
