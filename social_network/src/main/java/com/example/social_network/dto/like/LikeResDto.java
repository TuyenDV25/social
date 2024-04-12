package com.example.social_network.dto.like;

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
public class LikeResDto {
	
	@JsonProperty("userInfo")
	private UserInforResDto userInfo;
}
