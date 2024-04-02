package com.example.social_network.dto.post;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PostPostReqDto {

	private String content;

	private String privacy;

	@JsonProperty("image")
	private String imageId;
}
