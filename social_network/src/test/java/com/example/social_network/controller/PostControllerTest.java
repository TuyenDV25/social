package com.example.social_network.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
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
import org.springframework.web.multipart.MultipartFile;

import com.example.social_network.dto.post.DeletePostResDto;
import com.example.social_network.dto.post.PostPostReqDto;
import com.example.social_network.dto.post.PostPostResDto;
import com.example.social_network.dto.post.PostPrivacyPutReqDto;
import com.example.social_network.dto.post.PostPutReqDto;
import com.example.social_network.service.PostService;

@ExtendWith(MockitoExtension.class)
public class PostControllerTest {
	@Mock
	private PostService postService;

	private PostController postController;

	@BeforeEach
	void initData() {
		postController = new PostController(postService);
	}

	@Test
	void CreatePost_validRequest_success() throws Exception {

		when(postService.createPost(any(), any())).thenReturn(mock(PostPostResDto.class));

		var input = new PostPostReqDto();
		MultipartFile[] file = { mock(MultipartFile.class) };

		var result = postController.createPost(input, file);

		assertEquals(1000, result.getCode());
		assertEquals("create post successfully", result.getMessage());

	}

	@Test
	void UpdatePost_validRequest_success() throws Exception {

		when(postService.update(any(), any(),any())).thenReturn(mock(PostPostResDto.class));

		var input = new PostPutReqDto();
		MultipartFile[] file = { mock(MultipartFile.class) };

		var result = postController.updatePost(1L,input, file);

		assertEquals(1000, result.getCode());
		assertEquals("update post successfully", result.getMessage());

	}

	@Test
	void UpdatePrivacy_validRequest_success() throws Exception {

		when(postService.updatePrivacy(any(), any())).thenReturn(mock(PostPostResDto.class));

		var input = new PostPrivacyPutReqDto();

		var result = postController.updatePrivacy(1L,input);

		assertEquals(1000, result.getCode());
		assertEquals("update Post privacy successfully", result.getMessage());

	}

	@Test
	void DeletePost_validRequest_success() throws Exception {

		when(postService.delete(any())).thenReturn(mock(DeletePostResDto.class));

		var result = postController.deletePost(1L);

		assertEquals(1000, result.getCode());
		assertEquals("delete post successfully", result.getMessage());
	}

	@Test
	void GetPost_validRequest_success() throws Exception {

		when(postService.getPostDetail(any())).thenReturn(mock(PostPostResDto.class));

		var result = postController.getPost(1L);

		assertEquals(1000, result.getCode());
		assertEquals("get detail post successfully", result.getMessage());
	}

	@Test
	void GetUserAllPost_validRequest_success() throws Exception {

		List<PostPostResDto> listUser = new ArrayList<>();

		Page<PostPostResDto> userInfo = new PageImpl<>(listUser);
		when(postService.getUserAllPost(any(), any())).thenReturn(userInfo);

		var result = postController.getUserAllPost(1L, 1);

		assertEquals(1000, result.getCode());
		assertEquals("search user successfully", result.getMessage());
	}
	
	@Test
	void GetAllPostByKeyword_validRequest_success() throws Exception {

		List<PostPostResDto> listUser = new ArrayList<>();

		Page<PostPostResDto> userInfo = new PageImpl<>(listUser);
		when(postService.getAllPostByKeyword(any(), any())).thenReturn(userInfo);

		var result = postController.getAllPostByKeyword(1, "test");

		assertEquals(1000, result.getCode());
		assertEquals("search user successfully", result.getMessage());
	}
}
