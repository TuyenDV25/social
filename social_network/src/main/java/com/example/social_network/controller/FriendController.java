package com.example.social_network.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.social_network.dto.friend.FriendListResDto;
import com.example.social_network.dto.friend.FriendRequestListResDto;
import com.example.social_network.dto.friend.FriendRequestResDto;
import com.example.social_network.dto.friend.FriendResDto;
import com.example.social_network.response.BaseResponse;
import com.example.social_network.service.FriendService;
import com.example.social_network.utils.CommonConstants;

@RestController
@RequestMapping("api/v1/friend")
public class FriendController {

	@Autowired
	private FriendService friendService;

	@GetMapping("addRequest")
	BaseResponse<?> addFriendRequest(@RequestParam("id") Long friendRequestId) {
		friendService.addFriendRequest(friendRequestId);
		return BaseResponse.builder().message(CommonConstants.ADD_REQUEST_FRIEND_SUCCESS).build();
	}

	@GetMapping("removeRequest")
	BaseResponse<?> removeFriendRequest(@RequestParam("id") Long friendRequestId) {
		friendService.removeFriendRequest(friendRequestId);
		return BaseResponse.builder().message(CommonConstants.REMOVE_REQUEST_FRIEND_SUCCESS).build();
	}

	@GetMapping("list-request")
	BaseResponse<FriendRequestListResDto> getListRequest(@RequestParam Integer pageNumber,
			@RequestParam Integer pageSize) {
		Pageable paging = PageRequest.of(pageNumber, pageSize);
		Page<FriendRequestResDto> result = friendService.getListRequest(paging);

		return BaseResponse.<FriendRequestListResDto>builder()
				.result(FriendRequestListResDto.builder().listFriendRequestResDto(result).build())
				.message(CommonConstants.LIST_REQUEST).build();
	}

	@GetMapping("acceptFriend")
	BaseResponse<?> acceptFriendRequest(@RequestParam("id") Long friendRequestId) {
		friendService.acceptFriendRequest(friendRequestId);
		return BaseResponse.builder().message(CommonConstants.ACCEPT_REQUEST_FRIEND_SUCCESS).build();
	}

	@GetMapping("removeFriend")
	BaseResponse<?> removeFriend(@RequestParam("id") Long friendRequestId) {
		friendService.removeFriend(friendRequestId);
		return BaseResponse.builder().message(CommonConstants.REMOVE_FRIEND_SUCCESS).build();
	}

	@GetMapping("list-friend")
	BaseResponse<FriendListResDto> getListFriend(@RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
		Pageable paging = PageRequest.of(pageNumber, pageSize);
		Page<FriendResDto> result = friendService.getListFriend(paging);

		return BaseResponse.<FriendListResDto>builder()
				.result(FriendListResDto.builder().listFrientResDto(result).build())
				.message(CommonConstants.LIST_REQUEST).build();
	}

}
