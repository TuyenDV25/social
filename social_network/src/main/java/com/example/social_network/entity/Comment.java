package com.example.social_network.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "comment")
public class Comment extends AbstractEntity {

	@Column
	private String content;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserInfo user;

	@ManyToOne
	@JoinColumn(name = "post_id")
	private Post post;

	@OneToOne(mappedBy = "comment")
	@JsonBackReference
	private Image image;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public UserInfo getUser() {
		return user;
	}

	public void setUser(UserInfo user) {
		this.user = user;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

}
