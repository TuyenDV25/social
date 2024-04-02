package com.example.social_network.dto.image;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ImageReqDto {
	private Long id;

	@JsonProperty("link_image")
	private String linkImage;
}
