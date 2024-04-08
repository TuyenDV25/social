package com.example.social_network.dto.comment;

import com.example.social_network.dto.image.ImageResDto;
import com.example.social_network.dto.user.UserInforResDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentResDto {
	private String content;

	@JsonProperty("image")
	private ImageResDto image;

	@JsonProperty("userInfo")
	private UserInforResDto userInfo;

}
