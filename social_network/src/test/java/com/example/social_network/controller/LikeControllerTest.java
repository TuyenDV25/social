package com.example.social_network.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.social_network.dto.comment.LikeCommentReqDto;
import com.example.social_network.dto.post.LikePostReqDto;
import com.example.social_network.service.LikeService;

@ExtendWith(MockitoExtension.class)
public class LikeControllerTest {
	
	@Mock
	LikeService likeService;
	
	private LikeController likeController;
	
	@BeforeEach
	void initData() {
		likeController = new LikeController(likeService);
	}
	
	@Test
	void likePost_validRequest_success() {
		LikePostReqDto reqDto = new LikePostReqDto();
		reqDto.setId(1L);
		var result = likeController.likePost(reqDto);
		
		assertEquals(1000, result.getCode());
		assertEquals("handle like process successfully", result.getMessage());
	}
	
	@Test
	void likeComment_validRequest_success() {
		LikeCommentReqDto reqDto = new LikeCommentReqDto();
		reqDto.setId(1L);
		var result = likeController.likeComment(reqDto);
		
		assertEquals(1000, result.getCode());
		assertEquals("handle like process successfully", result.getMessage());
	}

}
