package com.example.social_network.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.social_network.dto.friend.FriendRequestResDto;
import com.example.social_network.dto.user.UserInforResDto;
import com.example.social_network.entity.Friend;
import com.example.social_network.entity.FriendRequest;
import com.example.social_network.entity.UserInfo;
import com.example.social_network.exception.AppException;
import com.example.social_network.exception.ErrorCode;
import com.example.social_network.mapper.user.UserMapper;
import com.example.social_network.repository.FriendRepository;
import com.example.social_network.repository.FriendRequestRepository;
import com.example.social_network.repository.UserInfoRepository;
import com.example.social_network.service.FriendService;
import com.example.social_network.service.UserService;

@Service
public class FriendServiceImpl implements FriendService {

	@Autowired
	UserInfoRepository userInfoRepository;

	@Autowired
	FriendRequestRepository friendRequestRepository;

	@Autowired
	FriendRepository friendRepository;
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserMapper userMapper;

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
		
		if (isFriend(createRequestuser, getRequestuser)) {
			throw new AppException(ErrorCode.ADD_REQUEST_TO_FRIEND);
		}
		
		if (isRequestFriend(createRequestuser, getRequestuser)) {
			throw new AppException(ErrorCode.ADD_REQUEST_AGAIN_TO_FRIEND);
		}
		
		FriendRequest request = new FriendRequest();
		request.setUserInfo(createRequestuser);
		friendRequestRepository.save(request);
		List<FriendRequest> listRequest = getRequestuser.getListFriendRequest();
		listRequest.add(request);
		getRequestuser.setListFriendRequest(listRequest);
		userService.updateUserInfo(getRequestuser);
	}

	/**
	 * check if 2 user is friend
	 * 
	 * @param createRequestuser {@link UserInfo}
	 * @param getRequestuser    {@link UserInfo}
	 * @return true: is friend, false is not friend
	 */
	private boolean isFriend(UserInfo createRequestuser, UserInfo getRequestuser) {
		// check if have been friend
		List<Friend> listFriendOfCreatetRequestUser = createRequestuser.getListFriend();

		List<Friend> listFriendOfGetRequestUser = getRequestuser.getListFriend();

		for (Friend friend : listFriendOfGetRequestUser) {
			if (friend.getUserInfo().getId() == createRequestuser.getId()) {
				return true;
			}
		}

		for (Friend friend : listFriendOfCreatetRequestUser) {
			if (friend.getUserInfo().getId() == getRequestuser.getId()) {
				return true;
			}
		}

		return false;
	}
	
	/**
	 * check if have send the request
	 * 
	 * @param createRequestuser {@link UserInfo}
	 * @param getRequestuser {@link UserInfo}
	 * @return true: have send request, false have not send request
	 */
	private boolean isRequestFriend(UserInfo createRequestuser, UserInfo getRequestuser) {

		List<FriendRequest> listFriendOfGetRequestUser = getRequestuser.getListFriendRequest();

		for (FriendRequest friend : listFriendOfGetRequestUser) {
			if (friend.getUserInfo().getId() == createRequestuser.getId()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void removeFriendRequest(Long friendRequestId) {
		
		UserInfo createRequestuser = userInfoRepository
				.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).get();
		UserInfo getRequestuser = userInfoRepository.findOneById(friendRequestId);
		if (getRequestuser == null) {
			throw new AppException(ErrorCode.USER_NOT_EXISTED);
		}
		
		if (!isRequestFriend(createRequestuser, getRequestuser)) {
			throw new AppException(ErrorCode.REMOVE_REQUEST_AGAIN_TO_FRIEND);
		}
		
		if (isFriend(createRequestuser, getRequestuser)) {
			throw new AppException(ErrorCode.REMOVE_REQUEST_TO_FRIEND);
		}
		
		List<FriendRequest> listFriendOfGetRequestUser = getRequestuser.getListFriendRequest();
		
		for (FriendRequest friend : listFriendOfGetRequestUser) {
			if (friend.getUserInfo().getId() == createRequestuser.getId()) {
				listFriendOfGetRequestUser.remove(friend);
				getRequestuser.setListFriendRequest(listFriendOfGetRequestUser);
				userService.updateUserInfo(getRequestuser);
				friendRequestRepository.delete(friend);
			}
		}
	}

	@Override
	public Page<FriendRequestResDto> getListRequest(Pageable page) {
		Page<FriendRequest> result = friendRequestRepository.findAll(page);
		List<FriendRequestResDto> listUserInforResDto = result.stream().map(
				request-> {
				FriendRequestResDto friendRequestResDto = new FriendRequestResDto();
				friendRequestResDto.setUserInfo(userMapper.entityToDto(request.getUserInfo()));
				return friendRequestResDto;
				}).collect(Collectors.toList());
		
		return new PageImpl<>(listUserInforResDto, page, listUserInforResDto.size());
	}

}
