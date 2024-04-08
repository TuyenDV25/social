package com.example.social_network.dto.friend;

import org.springframework.data.domain.Page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FriendRequestListResDto {
	private Page<FriendRequestResDto> listFriendRequestResDto;
}
