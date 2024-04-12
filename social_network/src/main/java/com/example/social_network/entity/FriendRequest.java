package com.example.social_network.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@SQLDelete(sql = "UPDATE friend_request SET deleted = true WHERE id=?")
@SQLRestriction(value = "deleted = false")
public class FriendRequest extends AbstractEntity{

	@ManyToOne
	@JoinColumn(name = "userInfo_id", updatable = false)
	private UserInfo userInfo;

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

}
