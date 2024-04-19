package com.example.social_network.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.example.social_network.dto.friend.FriendRequestResDto;
import com.example.social_network.dto.friend.FriendResDto;
import com.example.social_network.service.FriendService;

@ExtendWith(MockitoExtension.class)
public class FriendControllerTest {

	@Mock
	FriendService friendService;

	private FriendController friendController;

	@BeforeEach
	void initData() {
		friendController = new FriendController(friendService);
	}

	@Test
	void AddFriendRequest_validRequest_success() throws Exception {

		when(friendService.addFriendRequest(any())).thenReturn(1);

		var result = friendController.addFriendRequest(1L);

		assertEquals(1000, result.getCode());
		assertEquals("accept request friend successfully", result.getMessage());
	}

	@Test
	void AddFriendRequest_validRequest_AddFriend_success() throws Exception {

		when(friendService.addFriendRequest(any())).thenReturn(2);

		var result = friendController.addFriendRequest(1L);

		assertEquals(1000, result.getCode());
		assertEquals("add request friend successfully", result.getMessage());
	}

	@Test
	void RemoveFriendRequest_validRequest_success() throws Exception {

		var result = friendController.removeFriendRequest(1L);

		assertEquals(1000, result.getCode());
		assertEquals("remove friend request successfully", result.getMessage());
	}

	@Test
	void GetListRequest_validRequest_success() throws Exception {
		List<FriendRequestResDto> listUser = new ArrayList<>();

		Page<FriendRequestResDto> userInfo = new PageImpl<>(listUser);
		when(friendService.getListRequest(any())).thenReturn(userInfo);

		var result = friendController.getListRequest(0, 10);

		assertEquals(1000, result.getCode());
		assertEquals("get list request successfully", result.getMessage());
	}

	@Test
	void AcceptFriendRequest_validRequest_success() throws Exception {

		var result = friendController.acceptFriendRequest(1L);

		assertEquals(1000, result.getCode());
		assertEquals("accept request friend successfully", result.getMessage());
	}

	@Test
	void RemoveFriend_validRequest_success() throws Exception {
		var result = friendController.removeFriend(1L);

		assertEquals(1000, result.getCode());
		assertEquals("remove friend successfully", result.getMessage());
	}

	@Test
	void GetListFriend_validRequest_success() throws Exception {
		List<FriendResDto> listUser = new ArrayList<>();

		Page<FriendResDto> userInfo = new PageImpl<>(listUser);
		var pageable = PageRequest.of(0, 10);

		when(friendService.getListFriend(pageable)).thenReturn(userInfo);

		var result = friendController.getListFriend(0, 10);

		assertEquals(1000, result.getCode());
		assertEquals("get list request successfully", result.getMessage());
	}

}
