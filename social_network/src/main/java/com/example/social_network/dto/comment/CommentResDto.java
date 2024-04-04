package com.example.social_network.dto.comment;

import com.example.social_network.dto.image.ImageResDto;
import com.example.social_network.dto.user.UserInforResDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentResDto {
	String content;

	@JsonProperty("image")
	ImageResDto image;

	@JsonProperty("userInfo")
	UserInforResDto userInfo;

}
