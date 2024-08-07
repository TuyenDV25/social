package com.example.social_network.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.social_network.dto.friend.FriendRequestResDto;
import com.example.social_network.dto.friend.FriendResDto;
import com.example.social_network.dto.utils.user.UserInfoResponseUtils;
import com.example.social_network.entity.Friend;
import com.example.social_network.entity.FriendRequest;
import com.example.social_network.entity.UserInfo;
import com.example.social_network.exception.AppException;
import com.example.social_network.exception.ErrorCode;
import com.example.social_network.mapper.user.FriendUserInfo;
import com.example.social_network.repository.FriendRepository;
import com.example.social_network.repository.FriendRequestRepository;
import com.example.social_network.repository.UserInfoRepository;
import com.example.social_network.service.FriendService;
import com.example.social_network.service.UserService;
import com.example.social_network.utils.CommonConstants;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {

	private final UserInfoRepository userInfoRepository;

	private final FriendRequestRepository friendRequestRepository;

	private final FriendRepository friendRepository;

	private final UserService userService;

	private final UserInfoResponseUtils userInfoResponseUtils;

	/**
	 * add request friend
	 */
	@Override
	public Integer addFriendRequest(Long friendRequestId) {

		UserInfo createRequestuser = userInfoRepository
				.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
				.orElseThrow(() -> new UsernameNotFoundException(CommonConstants.USER_NOT_FOUND));

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

		// if have request then add friend
		if (isRequestFriend(getRequestuser, createRequestuser)) {
			// add friend to list friend
			List<Friend> listFriendOfcreateRequestUser = createRequestuser.getListFriend();
			Friend friend = new Friend();
			friend.setUserInfo(getRequestuser);
			friendRepository.save(friend);
			listFriendOfcreateRequestUser.add(friend);
			createRequestuser.setListFriend(listFriendOfcreateRequestUser);
			userService.updateUserInfo(getRequestuser);
			
			//delete friend request
			List<FriendRequest> listFriendRequestOfGetRequestUser = createRequestuser.getListFriendRequest();

			for (FriendRequest friendRequest : listFriendRequestOfGetRequestUser) {
				if (friendRequest.getUserInfo().getId() == getRequestuser.getId()) {
					listFriendRequestOfGetRequestUser.remove(friendRequest);
					createRequestuser.setListFriendRequest(listFriendRequestOfGetRequestUser);
					userService.updateUserInfo(createRequestuser);
					friendRequestRepository.delete(friendRequest);
					break;
				}
			}

			List<Friend> listFriendOfGetRequestUser = getRequestuser.getListFriend();
			friend = new Friend();
			friend.setUserInfo(createRequestuser);
			friendRepository.save(friend);
			listFriendOfGetRequestUser.add(friend);
			getRequestuser.setListFriend(listFriendOfGetRequestUser);
			userService.updateUserInfo(getRequestuser);
			return 1;
		}

		FriendRequest request = new FriendRequest();
		request.setUserInfo(createRequestuser);
		friendRequestRepository.save(request);
		List<FriendRequest> listRequest = getRequestuser.getListFriendRequest();
		listRequest.add(request);
		getRequestuser.setListFriendRequest(listRequest);
		userService.updateUserInfo(getRequestuser);
		return 2;
	}

	/**
	 * check if 2 user is friend
	 * 
	 * @param createRequestuser {@link UserInfo}
	 * @param getRequestuser    {@link UserInfo}
	 * @return true: is friend, false is not friend
	 */
	private boolean isFriend(UserInfo createRequestuser, UserInfo getRequestuser) {
		List<Friend> listFriendOfGetRequestUser = getRequestuser.getListFriend();

		for (Friend friend : listFriendOfGetRequestUser) {
			if (friend.getUserInfo().getId() == createRequestuser.getId()) {
				return true;
			}
		}

		return false;
	}

	/**
	 * check if have send the request
	 * 
	 * @param createRequestuser {@link UserInfo}
	 * @param getRequestuser    {@link UserInfo}
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

	/**
	 * remove friend request
	 */
	@Override
	public void removeFriendRequest(Long friendRequestId) {

		UserInfo createRequestuser = userInfoRepository
				.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
				.orElseThrow(() -> new UsernameNotFoundException(CommonConstants.USER_NOT_FOUND));

		UserInfo getRequestuser = userInfoRepository.findOneById(friendRequestId);

		if (createRequestuser.getId() == friendRequestId) {
			throw new AppException(ErrorCode.REMOVE_FRIENDREQUEST_YOURSELF);
		}

		if (getRequestuser == null) {
			throw new AppException(ErrorCode.USER_NOT_EXISTED);
		}

		if (!isRequestFriend(createRequestuser, getRequestuser)
				&& !isRequestFriend(getRequestuser, createRequestuser)) {
			throw new AppException(ErrorCode.NOT_EXIST_REQUEST);
		}

		List<FriendRequest> listFriendOfGetRequestUser = getRequestuser.getListFriendRequest();

		List<FriendRequest> listFriendOfcreateRequestUser = createRequestuser.getListFriendRequest();
		// user got request cancel request
		for (FriendRequest friend : listFriendOfGetRequestUser) {
			if (friend.getUserInfo().getId() == createRequestuser.getId()) {
				listFriendOfGetRequestUser.remove(friend);
				getRequestuser.setListFriendRequest(listFriendOfGetRequestUser);
				userService.updateUserInfo(getRequestuser);
				friendRequestRepository.delete(friend);
				return;
			}
		}
		// user send request cancel request
		for (FriendRequest friend : listFriendOfcreateRequestUser) {
			if (friend.getUserInfo().getId() == getRequestuser.getId()) {
				listFriendOfcreateRequestUser.remove(friend);
				createRequestuser.setListFriendRequest(listFriendOfcreateRequestUser);
				userService.updateUserInfo(createRequestuser);
				friendRequestRepository.delete(friend);
				return;
			}
		}
	}

	/**
	 * get list request friend
	 */
	@Override
	public Page<FriendRequestResDto> getListRequest(Pageable page) {

		UserInfo createRequestuser = userInfoRepository
				.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
				.orElseThrow(() -> new UsernameNotFoundException(CommonConstants.USER_NOT_FOUND));

		Page<FriendRequest> result = friendRequestRepository.findByUserInfo(createRequestuser.getId(), page);
		List<FriendRequestResDto> listUserInforResDto = result.stream().map(request -> {
			FriendRequestResDto friendRequestResDto = new FriendRequestResDto();
			friendRequestResDto.setCreatedDate(request.getCreatedDate());
			friendRequestResDto.setUserInfo(userInfoResponseUtils.convert(request.getUserInfo()));
			return friendRequestResDto;
		}).collect(Collectors.toList());

		return new PageImpl<>(listUserInforResDto, page, listUserInforResDto.size());
	}

	/**
	 * accept friend
	 */
	@Override
	public void acceptFriendRequest(Long friendRequestId) {

		UserInfo getRequestuser = userInfoRepository
				.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
				.orElseThrow(() -> new UsernameNotFoundException(CommonConstants.USER_NOT_FOUND));
		UserInfo createRequestuser = userInfoRepository.findOneById(friendRequestId);

		if (createRequestuser == null) {
			throw new AppException(ErrorCode.USER_NOT_EXISTED);
		}

		if (getRequestuser.getId() == friendRequestId) {
			throw new AppException(ErrorCode.ACCEPT_YOUR_REQUEST);
		}

		if (!isRequestFriend(createRequestuser, getRequestuser)) {
			throw new AppException(ErrorCode.NOT_EXIST_FRIEND_REQUEST);
		}

		List<FriendRequest> listFriendRequestOfGetRequestUser = getRequestuser.getListFriendRequest();

		for (FriendRequest friendRequest : listFriendRequestOfGetRequestUser) {
			if (friendRequest.getUserInfo().getId() == createRequestuser.getId()) {
				listFriendRequestOfGetRequestUser.remove(friendRequest);
				getRequestuser.setListFriendRequest(listFriendRequestOfGetRequestUser);
				userService.updateUserInfo(getRequestuser);
				friendRequestRepository.delete(friendRequest);
				break;
			}
		}

		// add friend to list friend
		List<Friend> listFriendOfcreateRequestUser = createRequestuser.getListFriend();
		Friend friend = new Friend();
		friend.setUserInfo(getRequestuser);
		friendRepository.save(friend);
		listFriendOfcreateRequestUser.add(friend);
		createRequestuser.setListFriend(listFriendOfcreateRequestUser);
		userService.updateUserInfo(getRequestuser);

		List<Friend> listFriendOfGetRequestUser = getRequestuser.getListFriend();
		friend = new Friend();
		friend.setUserInfo(createRequestuser);
		friendRepository.save(friend);
		listFriendOfGetRequestUser.add(friend);
		getRequestuser.setListFriend(listFriendOfGetRequestUser);
		userService.updateUserInfo(getRequestuser);
	}

	/**
	 * remove friend
	 */
	@Override
	public void removeFriend(Long friendRequestId) {

		UserInfo createRequestuser = userInfoRepository
				.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
				.orElseThrow(() -> new UsernameNotFoundException(CommonConstants.USER_NOT_FOUND));
		UserInfo getRequestuser = userInfoRepository.findOneById(friendRequestId);

		if (getRequestuser == null) {
			throw new AppException(ErrorCode.USER_NOT_EXISTED);
		}

		if (createRequestuser.getId() == friendRequestId) {
			throw new AppException(ErrorCode.REMOVE_FRIEND_YOURSELF);
		}

		if (!isFriend(createRequestuser, getRequestuser)) {
			throw new AppException(ErrorCode.NOT_FRIEND);
		}

		List<Friend> friendSourceList = createRequestuser.getListFriend();
		List<Friend> friendTargetList = getRequestuser.getListFriend();
		for (Friend friend : friendSourceList) {
			if (friend.getUserInfo().getId() == getRequestuser.getId()) {
				friendSourceList.remove(friend);
				createRequestuser.setListFriend(friendSourceList);
				userService.updateUserInfo(createRequestuser);
				friendRepository.deleteById(friend.getId());
				break;
			}
		}
		for (Friend friend : friendTargetList) {
			if (friend.getUserInfo().getId() == createRequestuser.getId()) {
				friendTargetList.remove(friend);
				getRequestuser.setListFriend(friendTargetList);
				userService.updateUserInfo(getRequestuser);
				friendRepository.deleteById(friend.getId());
				break;
			}
		}

	}

	/**
	 * get list friend
	 */
	@Override
	public Page<FriendResDto> getListFriend(Pageable page) {

		UserInfo createRequestuser = userInfoRepository
				.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
				.orElseThrow(() -> new UsernameNotFoundException(CommonConstants.USER_NOT_FOUND));

		Page<FriendUserInfo> result = friendRepository.findByUserInfo(createRequestuser.getId(), page);
		List<FriendResDto> listUserInforResDto = result.stream().map(friend -> {
			FriendResDto friendResDto = new FriendResDto();
			friendResDto.setCreatedDate(friend.getCreatedDate());
			friendResDto.setUserInfo(userInfoResponseUtils.convert(userService.findOneById(friend.getUserInfoId())));
			return friendResDto;
		}).collect(Collectors.toList());

		return new PageImpl<>(listUserInforResDto, page, listUserInforResDto.size());
	}

}
