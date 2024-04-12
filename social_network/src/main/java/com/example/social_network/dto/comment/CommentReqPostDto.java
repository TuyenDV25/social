package com.example.social_network.dto.comment;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentReqPostDto {
	
	Long id;

	private String content;

	@JsonProperty("image")
	private MultipartFile uploadFile;
}
