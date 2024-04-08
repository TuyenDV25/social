package com.example.social_network.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.social_network.dto.friend.FriendRequestResDto;

public interface FriendService {

	void addFriendRequest(Long friendRequestId);

	void removeFriendRequest(Long friendRequestId);

	Page<FriendRequestResDto> getListRequest(Pageable page);
}
