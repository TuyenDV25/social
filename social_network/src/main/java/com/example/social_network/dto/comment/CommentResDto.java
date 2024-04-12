package com.example.social_network.dto.comment;

import java.util.List;

import com.example.social_network.dto.image.ImageResDto;
import com.example.social_network.dto.user.UserInforResDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentResDto {
	private String content;

	@JsonProperty("image")
	private List<ImageResDto> image;

	@JsonProperty("userInfo")
	private UserInforResDto userInfo;

}
