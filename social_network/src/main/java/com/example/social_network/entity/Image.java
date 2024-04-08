package com.example.social_network.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "image")
public class Image extends AbstractEntity {

	private String linkImage;

	@ManyToOne
	@JoinColumn(name = "userInfo_id")
	private UserInfo userInfo;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "post_id", referencedColumnName = "id")
	@JsonManagedReference
	private Post post;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "comment_id", referencedColumnName = "id")
	@JsonManagedReference
	private Comment comment;

	public String getLinkImage() {
		return linkImage;
	}

	public void setLinkImage(String linkImage) {
		this.linkImage = linkImage;
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

	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

}
