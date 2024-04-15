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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("api/v1/friend")
public class FriendController {

	@Autowired
	private FriendService friendService;

	@GetMapping("addRequest")
	@Operation(summary = "API add request Friend", description = "add Request add Friend")
	@ApiResponse(responseCode = "200", description = "add request successfully")
	@ApiResponse(responseCode = "400", description = "add request error")
	BaseResponse<?> addFriendRequest(@RequestParam("id") Long friendRequestId) {
		friendService.addFriendRequest(friendRequestId);
		return BaseResponse.builder().message(CommonConstants.ADD_REQUEST_FRIEND_SUCCESS).build();
	}

	@GetMapping("removeRequest")
	@Operation(summary = "API remove request Friend", description = "remove Request add Friend")
	@ApiResponse(responseCode = "200", description = "remove request successfully")
	@ApiResponse(responseCode = "400", description = "remove request error")
	BaseResponse<?> removeFriendRequest(@RequestParam("id") Long friendRequestId) {
		friendService.removeFriendRequest(friendRequestId);
		return BaseResponse.builder().message(CommonConstants.REMOVE_REQUEST_FRIEND_SUCCESS).build();
	}

	@GetMapping("list-request")
	@Operation(summary = "API get list request Friend")
	@ApiResponse(responseCode = "200", description = "get list request successfully")
	@ApiResponse(responseCode = "400", description = "get list request error")
	BaseResponse<FriendRequestListResDto> getListRequest(@RequestParam Integer pageNumber,
			@RequestParam Integer pageSize) {
		Pageable paging = PageRequest.of(pageNumber, pageSize);
		Page<FriendRequestResDto> result = friendService.getListRequest(paging);

		return BaseResponse.<FriendRequestListResDto>builder()
				.result(FriendRequestListResDto.builder().listFriendRequestResDto(result).build())
				.message(CommonConstants.LIST_REQUEST).build();
	}

	@GetMapping("acceptFriend")
	@Operation(summary = "API accept request Friend")
	@ApiResponse(responseCode = "200", description = "accept request successfully")
	@ApiResponse(responseCode = "400", description = "accept request error")
	BaseResponse<?> acceptFriendRequest(@RequestParam("id") Long friendRequestId) {
		friendService.acceptFriendRequest(friendRequestId);
		return BaseResponse.builder().message(CommonConstants.ACCEPT_REQUEST_FRIEND_SUCCESS).build();
	}

	@GetMapping("removeFriend")
	@Operation(summary = "API remove Friend")
	@ApiResponse(responseCode = "200", description = "remove friend successfully")
	@ApiResponse(responseCode = "400", description = "remove friend  error")
	BaseResponse<?> removeFriend(@RequestParam("id") Long friendRequestId) {
		friendService.removeFriend(friendRequestId);
		return BaseResponse.builder().message(CommonConstants.REMOVE_FRIEND_SUCCESS).build();
	}

	@GetMapping("list-friend")
	@Operation(summary = "API get list Friend")
	@ApiResponse(responseCode = "200", description = "get list friend successfully")
	@ApiResponse(responseCode = "400", description = "get list friend  error")
	BaseResponse<FriendListResDto> getListFriend(@RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
		Pageable paging = PageRequest.of(pageNumber, pageSize);
		Page<FriendResDto> result = friendService.getListFriend(paging);

		return BaseResponse.<FriendListResDto>builder()
				.result(FriendListResDto.builder().listFrientResDto(result).build())
				.message(CommonConstants.LIST_REQUEST).build();
	}

}
