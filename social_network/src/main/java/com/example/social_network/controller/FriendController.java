package com.example.social_network.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/friend")
public class FriendController {

	private final FriendService friendService;

	@PostMapping("addRequest/{getFriendRequestUserId}")
	@Operation(summary = "API add request Friend", description = "add Request add Friend")
	@ApiResponse(responseCode = "200", description = "add request successfully")
	@ApiResponse(responseCode = "400", description = "add request error")
	@ApiResponse(responseCode = "401", description = "UserName not found")
	@ApiResponse(responseCode = "404", description = "add friend to not exist user")
	BaseResponse<?> addFriendRequest(@PathVariable Long getFriendRequestUserId) {
		Integer AddFriendResult = friendService.addFriendRequest(getFriendRequestUserId);
		return BaseResponse.builder().message(AddFriendResult == 2 ? CommonConstants.ADD_REQUEST_FRIEND_SUCCESS
				: CommonConstants.ACCEPT_REQUEST_FRIEND_SUCCESS).build();
	}

	@PutMapping("declineRequest/{declineFriendRequestUserId}")
	@Operation(summary = "API remove request Friend", description = "remove Request add Friend")
	@ApiResponse(responseCode = "200", description = "remove request successfully")
	@ApiResponse(responseCode = "400", description = "remove request error")
	@ApiResponse(responseCode = "401", description = "UserName not found")
	@ApiResponse(responseCode = "404", description = "add friend to not exist user")
	BaseResponse<?> removeFriendRequest(@PathVariable  Long declineFriendRequestUserId) {
		friendService.removeFriendRequest(declineFriendRequestUserId);
		return BaseResponse.builder().message(CommonConstants.REMOVE_REQUEST_FRIEND_SUCCESS).build();
	}

	@GetMapping("list-request")
	@Operation(summary = "API get list request Friend")
	@ApiResponse(responseCode = "200", description = "get list request successfully")
	@ApiResponse(responseCode = "401", description = "UserName not found")
	BaseResponse<FriendRequestListResDto> getListRequest(@RequestParam Integer pageNumber,
			@RequestParam Integer pageSize) {
		Pageable paging = PageRequest.of(pageNumber, pageSize);
		Page<FriendRequestResDto> result = friendService.getListRequest(paging);

		return BaseResponse.<FriendRequestListResDto>builder()
				.result(FriendRequestListResDto.builder().listFriendRequestResDto(result).build())
				.message(CommonConstants.LIST_REQUEST).build();
	}

	@PutMapping("acceptFriend/{friendRequestUserId}")
	@Operation(summary = "API accept request Friend")
	@ApiResponse(responseCode = "200", description = "accept request successfully")
	@ApiResponse(responseCode = "400", description = "accept request error")
	@ApiResponse(responseCode = "401", description = "UserName not found")
	@ApiResponse(responseCode = "404", description = "add friend to not exist user")
	BaseResponse<?> acceptFriendRequest(@PathVariable  Long friendRequestUserId) {
		friendService.acceptFriendRequest(friendRequestUserId);
		return BaseResponse.builder().message(CommonConstants.ACCEPT_REQUEST_FRIEND_SUCCESS).build();
	}

	@PutMapping("removeFriend/{friendId}")
	@Operation(summary = "API remove Friend")
	@ApiResponse(responseCode = "200", description = "remove friend successfully")
	@ApiResponse(responseCode = "400", description = "remove friend  error")
	@ApiResponse(responseCode = "401", description = "UserName not found")
	@ApiResponse(responseCode = "404", description = "add friend to not exist user")
	BaseResponse<?> removeFriend(@PathVariable  Long friendId) {
		friendService.removeFriend(friendId);
		return BaseResponse.builder().message(CommonConstants.REMOVE_FRIEND_SUCCESS).build();
	}

	@GetMapping("list-friend")
	@Operation(summary = "API get list Friend")
	@ApiResponse(responseCode = "200", description = "get list friend successfully")
	@ApiResponse(responseCode = "400", description = "get list friend  error")
	@ApiResponse(responseCode = "401", description = "UserName not found")
	BaseResponse<FriendListResDto> getListFriend(@RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
		Pageable paging = PageRequest.of(pageNumber, pageSize);
		Page<FriendResDto> result = friendService.getListFriend(paging);

		return BaseResponse.<FriendListResDto>builder()
				.result(FriendListResDto.builder().listFrientResDto(result).build())
				.message(CommonConstants.LIST_REQUEST).build();
	}

}
