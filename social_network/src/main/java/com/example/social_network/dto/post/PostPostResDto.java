package com.example.social_network.dto.post;

import java.util.ArrayList;
import java.util.List;

import com.example.social_network.dto.AbstractDto;
import com.example.social_network.dto.comment.CommentResDto;
import com.example.social_network.dto.image.ImageResDto;
import com.example.social_network.dto.like.LikeResDto;
import com.example.social_network.dto.user.UserInforResDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PostPostResDto extends AbstractDto {

	private Long id;

	private String content;

	private Integer privacy;

	@JsonProperty("image")
	private List<ImageResDto> image;

	@JsonProperty("likes")
	private List<LikeResDto> likes = new ArrayList<>();

	@JsonProperty("comment")
	private List<CommentResDto> comments = new ArrayList<>();

	@JsonProperty("like_count")
	private Long likeCount;

	@JsonProperty("cmt_count")
	private Long commentCount;

	@JsonProperty("userInfo")
	private UserInforResDto userInfo;

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

	public List<ImageResDto> getImage() {
		return image;
	}

	public void setImage(List<ImageResDto> image) {
		this.image = image;
	}

	public List<LikeResDto> getLikes() {
		return likes;
	}

	public void setLikes(List<LikeResDto> likes) {
		this.likes = likes;
	}

	public List<CommentResDto> getComments() {
		return comments;
	}

	public void setComments(List<CommentResDto> comments) {
		this.comments = comments;
	}

	public Long getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(Long likeCount) {
		this.likeCount = likeCount;
	}

	public Long getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Long commentCount) {
		this.commentCount = commentCount;
	}

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
}
