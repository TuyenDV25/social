package com.example.social_network.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "post")
public class Post extends AbstractEntity {

	@ManyToOne
	@JoinColumn(name = "userInfo_id", updatable = false)
	private UserInfo userInfo;

	@Column
	private String content;

	/**
	 * 1: public 2: friend 3: only me
	 */
	@Column
	private Integer privacy;

	@OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
	private List<Comment> comments = new ArrayList<>();

	@OneToOne(mappedBy = "post")
	@JsonBackReference
	private Image image;

	@OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
	private List<Like> likes = new ArrayList<>();

	@OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
	private List<Share> shares = new ArrayList<>();

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getPrivacy() {
		return privacy;
	}

	public void setPrivacy(Integer privacy) {
		this.privacy = privacy;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public List<Like> getLikes() {
		return likes;
	}

	public void setLikes(List<Like> likes) {
		this.likes = likes;
	}

	public List<Share> getShares() {
		return shares;
	}

	public void setShares(List<Share> shares) {
		this.shares = shares;
	}

}
