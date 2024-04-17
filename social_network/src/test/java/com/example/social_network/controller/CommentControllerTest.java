package com.example.social_network.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import com.example.social_network.dto.comment.CommentReqPostDto;
import com.example.social_network.dto.comment.CommentReqPutDto;
import com.example.social_network.dto.comment.CommentResDto;
import com.example.social_network.service.CommentService;

@ExtendWith(MockitoExtension.class)
public class CommentControllerTest {
	@Mock
	private CommentService commentService;

	private CommentController commentController;

	@BeforeEach
	void initData() {
		commentController = new CommentController(commentService);
	}

	@Test
	void CreateComment_validRequest_success() {
		when(commentService.createComment(any(), any(), any())).thenReturn(mock(CommentResDto.class));

		var input = new CommentReqPostDto();
		MultipartFile file = mock(MultipartFile.class);

		var result = commentController.createComment(1L, input, file);

		assertEquals(1000, result.getCode());
		assertEquals("comment post successfully", result.getMessage());
	}
	
	@Test
	void UpdateComment_validRequest_success() {
		when(commentService.updateComment(any(), any(), any())).thenReturn(mock(CommentResDto.class));

		var input = new CommentReqPutDto();
		MultipartFile file = mock(MultipartFile.class);

		var result = commentController.updateComment(1L, input, file);

		assertEquals(1000, result.getCode());
		assertEquals("update comment successfully", result.getMessage());
	}
	
	@Test
	void DeleteComment_validRequest_success() {

		var result = commentController.deleteComment(1L);

		assertEquals(1000, result.getCode());
		assertEquals("delete comment successfully", result.getMessage());
	}
	
	@Test
	void GetAllComment_validRequest_success() {

		var result = commentController.getAllComment(1L, 0);

		assertEquals(1000, result.getCode());
		assertEquals("search user successfully", result.getMessage());
	}

}
