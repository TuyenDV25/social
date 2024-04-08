package com.example.social_network.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.social_network.dto.friend.FriendRequestListResDto;
import com.example.social_network.dto.friend.FriendRequestResDto;
import com.example.social_network.response.BaseResponse;
import com.example.social_network.service.FriendService;
import com.example.social_network.utils.CommonConstants;

@RestController
@RequestMapping("api/v1/friend")
public class FriendController {

	@Autowired
	private FriendService friendService;

	@GetMapping("add/{id}")
	BaseResponse<?> addFriendRequest(@PathVariable("id") Long friendRequestId) {
		friendService.addFriendRequest(friendRequestId);
		return BaseResponse.builder().message(CommonConstants.ADD_REQUEST_FRIEND_SUCCESS).build();
	}

	@GetMapping("remove/{id}")
	BaseResponse<?> removeFriendRequest(@PathVariable("id") Long friendRequestId) {
		friendService.removeFriendRequest(friendRequestId);
		return BaseResponse.builder().message(CommonConstants.ADD_REQUEST_FRIEND_SUCCESS).build();
	}

	@GetMapping("list-request")
	BaseResponse<FriendRequestListResDto> removeFriendRequest(@RequestParam Integer pageNumber,
			@RequestParam Integer pageSize) {
		Pageable paging = PageRequest.of(pageNumber, pageSize);
		Page<FriendRequestResDto> result = friendService.getListRequest(paging);
		return BaseResponse.<FriendRequestListResDto>builder()
				.result(FriendRequestListResDto.builder().listFriendRequestResDto(result).build())
				.message(CommonConstants.LIST_REQUEST).build();
	}

}
