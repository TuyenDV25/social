package com.example.social_network.dto.post;

import java.util.ArrayList;
import java.util.List;

import com.example.social_network.dto.comment.CommentResDto;
import com.example.social_network.dto.image.ImageResDto;
import com.example.social_network.dto.like.LikeResDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostPostResDto {

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

}
