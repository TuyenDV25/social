package com.example.social_network.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.social_network.dto.friend.FriendRequestResDto;
import com.example.social_network.dto.friend.FriendResDto;

public interface FriendService {

	Integer addFriendRequest(Long friendRequestId);

	void removeFriendRequest(Long friendRequestId);

	Page<FriendRequestResDto> getListRequest(Pageable page);
	
	void acceptFriendRequest(Long friendRequestId);

	void removeFriend(Long friendRequestId);
	
	Page<FriendResDto> getListFriend(Pageable page);
}
