package com.example.social_network.dto.friend;

import com.example.social_network.dto.user.UserInforResDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FriendRequestResDto {
	private UserInforResDto userInfo;
}
