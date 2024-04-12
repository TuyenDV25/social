package com.example.social_network.dto.post;

import org.springframework.web.multipart.MultipartFile;

import com.example.social_network.enumdef.PostType;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostPostReqDto {
	
	Long id;

	private String content;

	private int privacy = PostType.PUBLIC.getCode();

	@JsonProperty("image")
	private MultipartFile uploadFile;
}
