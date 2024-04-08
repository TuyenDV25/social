package com.example.social_network.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;

@Entity
@Builder
@Table(name = "likes")
public class Like extends AbstractEntity {

	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserInfo userInfo;

	@ManyToOne
	@JoinColumn(name = "post_id")
	private Post post;

	@Column
	private boolean status;

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

}
