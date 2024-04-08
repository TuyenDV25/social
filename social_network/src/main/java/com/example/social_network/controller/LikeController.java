package com.example.social_network.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.social_network.response.BaseResponse;
import com.example.social_network.service.LikeService;
import com.example.social_network.utils.CommonConstants;

@RestController
@RequestMapping("api/v1/like")
public class LikeController {

	@Autowired
	private LikeService likeService;

	/**
	 * like or dislike post
	 * 
	 * @param postId
	 */
	@PostMapping("/{postId}")
	public BaseResponse<?> like(@PathVariable("postId") Long postId) {
		likeService.like(postId);
		return BaseResponse.builder().message(CommonConstants.LIKE_PROCESS).build();
	}

}
