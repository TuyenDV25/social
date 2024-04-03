package com.example.social_network.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.social_network.dto.post.DeletePostReqDto;
import com.example.social_network.dto.post.DeletePostResDto;
import com.example.social_network.dto.post.PostPostReqDto;
import com.example.social_network.dto.post.PostPostResDto;
import com.example.social_network.response.BaseResponse;
import com.example.social_network.service.PostService;
import com.example.social_network.utils.CommonConstants;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/post")
public class PostController {

	@Autowired
	private PostService postService;

	/**
	 * create post
	 * 
	 * @param reqDto {@link PostPostReqDto}
	 * @return {@link PostPostResDto}
	 */
	@PostMapping(value = "/create", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	BaseResponse<PostPostResDto> createPost(PostPostReqDto reqDto) {
		PostPostResDto resDto = postService.createPost(reqDto);
		return BaseResponse.<PostPostResDto>builder().result(resDto).message(CommonConstants.POST_CREATE_SUCCESS)
				.build();
	}

	/**
	 * update post
	 * 
	 * @param reqDto {@link PostPostReqDto}
	 * @return {@link PostPostResDto}
	 */
	@PutMapping(value = "/update", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	BaseResponse<PostPostResDto> updatePost(PostPostReqDto reqDto) {
		PostPostResDto resDto = postService.update(reqDto);
		return BaseResponse.<PostPostResDto>builder().result(resDto).message(CommonConstants.POST_UPDATE_SUCCESS)
				.build();
	}

	@PostMapping("/delete/{id}") // delete post
	public BaseResponse<DeletePostResDto> deletePost(@PathVariable("id") Long idPost) {
		DeletePostReqDto reqDto = new DeletePostReqDto(idPost);
		DeletePostResDto resDto = postService.delete(reqDto);
		return BaseResponse.<DeletePostResDto>builder().result(resDto).message(CommonConstants.POST_DELETE_SUCCESS)
				.build();
	}

}
