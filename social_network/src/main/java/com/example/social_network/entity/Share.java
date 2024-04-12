package com.example.social_network.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "share")
@SQLDelete(sql = "UPDATE share SET deleted = true WHERE id=?")
@SQLRestriction(value = "deleted = false")
public class Share extends AbstractEntity {

	@Column
	private String content;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserInfo userInfo;

	@ManyToOne
	@JoinColumn(name = "post_id")
	private Post post;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

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

}
