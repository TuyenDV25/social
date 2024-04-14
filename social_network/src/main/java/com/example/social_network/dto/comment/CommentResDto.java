package com.example.social_network.dto.comment;

import com.example.social_network.dto.AbstractDto;
import com.example.social_network.dto.image.ImageResDto;
import com.example.social_network.dto.user.UserInforResDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentResDto extends AbstractDto {
	private String content;

	@JsonProperty("image")
	private ImageResDto image;

	@JsonProperty("userInfo")
	private UserInforResDto userInfo;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public UserInforResDto getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInforResDto userInfo) {
		this.userInfo = userInfo;
	}

	public ImageResDto getImage() {
		return image;
	}

	public void setImage(ImageResDto image) {
		this.image = image;
	}
}
