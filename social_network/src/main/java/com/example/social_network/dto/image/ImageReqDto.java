package com.example.social_network.dto.image;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageReqDto {
	private Long id;

	@JsonProperty("link_image")
	private String linkImage;
}
