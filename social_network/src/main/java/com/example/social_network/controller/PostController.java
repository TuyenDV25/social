package com.example.social_network.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.social_network.dto.post.DeletePostReqDto;
import com.example.social_network.dto.post.DeletePostResDto;
import com.example.social_network.dto.post.PostListReqDto;
import com.example.social_network.dto.post.PostListResDto;
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
	@PutMapping(value = "/update/{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	BaseResponse<PostPostResDto> updatePost(@PathVariable("id") Long id, PostPostReqDto reqDto) {
		PostPostResDto resDto = postService.update(id, reqDto);
		return BaseResponse.<PostPostResDto>builder().result(resDto).message(CommonConstants.POST_UPDATE_SUCCESS)
				.build();
	}

	/**
	 * deletePost
	 * 
	 * @param idPost id pot will delete
	 * @return {@link DeletePostResDto}
	 */
	@PostMapping("/delete/{id}")
	public BaseResponse<DeletePostResDto> deletePost(@PathVariable("id") Long idPost) {
		DeletePostReqDto reqDto = new DeletePostReqDto(idPost);
		DeletePostResDto resDto = postService.delete(reqDto);
		return BaseResponse.<DeletePostResDto>builder().result(resDto).message(CommonConstants.POST_DELETE_SUCCESS)
				.build();
	}

	/**
	 * get detail post
	 * 
	 * @param idPost
	 * @return {@link PostPostResDto}
	 */
	@GetMapping("detail/{id}")
	public BaseResponse<PostPostResDto> getPost(@PathVariable("id") Long idPost) {
		PostPostResDto resDto = postService.getPostDetail(idPost);
		return BaseResponse.<PostPostResDto>builder().result(resDto).message(CommonConstants.POST_DETAIL_SUCCESS)
				.build();
	}

	/**
	 * get all post of user
	 * 
	 * @param id
	 * @param reqDto {@link PostListReqDto}
	 * @return List of {@link PostListResDto}
	 */
	@PostMapping("all/{id}")
	public BaseResponse<PostListResDto> getUserAllPost(@PathVariable("id") Long id,
			@Valid @RequestBody PostListReqDto reqDto) {
		Page<PostPostResDto> result = postService.getUserAllPost(id, reqDto);
		return BaseResponse.<PostListResDto>builder().result(PostListResDto.builder().listPost(result).build())
				.message(CommonConstants.USER_SEARCH_SUCCES).build();
	}
	
	/**
	 * list post contain content is searched
	 * 
	 * @param reqDto {@link PostListReqDto}
	 * @return {@link PostListResDto}
	 */
	@PostMapping("search")
	public BaseResponse<PostListResDto> getAllPostByKeyword(@Valid @RequestBody PostListReqDto reqDto) {
		Page<PostPostResDto> result = postService.getAllPostByKeyword(reqDto);
		return BaseResponse.<PostListResDto>builder().result(PostListResDto.builder().listPost(result).build())
				.message(CommonConstants.USER_SEARCH_SUCCES).build();
	}

}
