package com.example.social_network.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.social_network.dto.comment.LikeCommentReqDto;
import com.example.social_network.dto.post.LikePostReqDto;
import com.example.social_network.response.BaseResponse;
import com.example.social_network.service.LikeService;
import com.example.social_network.utils.CommonConstants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/like")
public class LikeController {

	private final LikeService likeService;

	/**
	 * like or dislike post
	 * 
	 * @param postId
	 */
	@PostMapping("/post")
	@Operation(summary = "API like post", description = "like or dislike a post")
	@ApiResponse(responseCode = "200", description = "like or dislike post successfully")
	@ApiResponse(responseCode = "400", description = "like or dislike post error")
	public BaseResponse<?> likePost(@Valid @RequestBody LikePostReqDto reqDto) {
		likeService.likePost(reqDto);
		return BaseResponse.builder().message(CommonConstants.LIKE_PROCESS).build();
	}

	/**
	 * like or dislike comment
	 * 
	 * @param commentId
	 */
	@PostMapping("/comment")
	@Operation(summary = "API like comment", description = "like or dislike a comment")
	@ApiResponse(responseCode = "200", description = "like or dislike comment successfully")
	@ApiResponse(responseCode = "400", description = "like or dislike comment error")
	public BaseResponse<?> likeComment(@Valid @RequestBody LikeCommentReqDto reqDto) {
		likeService.likeComment(reqDto);
		return BaseResponse.builder().message(CommonConstants.LIKE_PROCESS).build();
	}

}
