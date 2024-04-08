package com.example.social_network.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.social_network.response.BaseResponse;
import com.example.social_network.service.FriendService;

@RestController
@RequestMapping("api/v1/friend")
public class FriendController {

	@Autowired
	private FriendService friendService;

	@PostMapping("/{id}")
	BaseResponse<?> addFriendRequest(@PathVariable("id") Long friendRequestId) {
		friendService.addFriendRequest(friendRequestId);
		return null;
	}

}
