package com.example.social_network.dto.friend;

import org.springframework.data.domain.Page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class FriendListResDto {
	private Page<FriendResDto> listFrientResDto;
}
