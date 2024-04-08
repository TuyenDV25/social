package com.example.social_network.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.social_network.entity.Friend;
import com.example.social_network.entity.UserInfo;
import com.example.social_network.exception.AppException;
import com.example.social_network.exception.ErrorCode;
import com.example.social_network.repository.FriendRepository;
import com.example.social_network.repository.FriendRequestRepository;
import com.example.social_network.repository.UserInfoRepository;
import com.example.social_network.service.FriendService;

@Service
public class FriendServiceImpl implements FriendService {

	@Autowired
	UserInfoRepository userInfoRepository;
	
	@Autowired
	FriendRequestRepository friendRequestRepository;
	
	@Autowired
	FriendRepository friendRepository;

	@Override
	public void addFriendRequest(Long friendRequestId) {
		UserInfo createRequestuser = userInfoRepository
				.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).get();
		UserInfo getRequestuser = userInfoRepository.findOneById(friendRequestId);
		if (getRequestuser == null) {
			throw new AppException(ErrorCode.USER_NOT_EXISTED);
		}

		if (createRequestuser.getId().equals(friendRequestId)) {
			throw new AppException(ErrorCode.ADD_FRIEND_YOUSELF);
		}
		
		// check if have been friend
		List<Friend> listFriendOfCreatetRequestUser = createRequestuser.getListFriend();
		
		List<Friend> listFriendOfGetRequestUser = createRequestuser.getListFriend();
		
		
		

	}

}
