package com.example.social_network.dto.post;

import com.example.social_network.dto.image.ImageResDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PostPostResDto {

	private String content;

	private Integer privacy;

	@JsonProperty("image")
	private ImageResDto image;

	@JsonProperty("like_count")
	private Long likeCount;

	@JsonProperty("cmt_count")
	private Long commentCount;

	@JsonProperty("share_count")
	private Long shareCount;

	private Boolean liked;

}
