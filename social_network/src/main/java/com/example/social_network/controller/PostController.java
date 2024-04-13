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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.social_network.dto.post.DeletePostResDto;
import com.example.social_network.dto.post.PostListReqDto;
import com.example.social_network.dto.post.PostListResDto;
import com.example.social_network.dto.post.PostPostReqDto;
import com.example.social_network.dto.post.PostPostResDto;
import com.example.social_network.dto.post.PostPrivacyPutReqDto;
import com.example.social_network.dto.post.PostPutReqDto;
import com.example.social_network.response.BaseResponse;
import com.example.social_network.service.PostService;
import com.example.social_network.utils.CommonConstants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
	@PostMapping(value = "/create", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	@Operation(summary = "API create post", description = "create a post")
	@ApiResponse(responseCode = "200", description = "Tạo post thành công", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostPostReqDto.class)))
	@ApiResponse(responseCode = "400", description = "Tạo post không thành công")
	BaseResponse<PostPostResDto> createPost(@RequestPart(name = "reqDto", required = false) @Valid PostPostReqDto reqDto,
			@RequestPart(name = "multipartFile", required = false) MultipartFile[] multipartFile) {
		PostPostResDto resDto = postService.createPost(reqDto, multipartFile);
		return BaseResponse.<PostPostResDto>builder().result(resDto).message(CommonConstants.POST_CREATE_SUCCESS)
				.build();
	}

	/**
	 * update post
	 * 
	 * @param reqDto {@link PostPutReqDto}
	 * @return {@link PostPostResDto}
	 */
	@PutMapping(value = "/update/{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	@Operation(summary = "API update post", description = "Update Post")
	@ApiResponse(responseCode = "200", description = "Update post successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostPutReqDto.class)))
	@ApiResponse(responseCode = "400", description = "Update post error")
	BaseResponse<PostPostResDto> updatePost(@RequestPart @Valid PostPutReqDto reqDto,
			@RequestPart(required = false) MultipartFile[] multipartFile) {
		PostPostResDto resDto = postService.update(reqDto, multipartFile);
		return BaseResponse.<PostPostResDto>builder().result(resDto).message(CommonConstants.POST_UPDATE_SUCCESS)
				.build();
	}

	@PutMapping(value = "/update-privacy")
	@Operation(summary = "API update privacy", description = "Update privacy of post")
	@ApiResponse(responseCode = "200", description = "Update privacy thành công", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostPrivacyPutReqDto.class)))
	@ApiResponse(responseCode = "400", description = "Update privacy không thành công")
	BaseResponse<PostPostResDto> updatePrivacy(@Valid @RequestBody PostPrivacyPutReqDto reqDto) {
		PostPostResDto resDto = postService.updatePrivacy(reqDto);
		return BaseResponse.<PostPostResDto>builder().result(resDto)
				.message(CommonConstants.POST_UPDATE_PRIVACY_SUCCESS).build();
	}

	/**
	 * delete post
	 * 
	 * @param idPost
	 * @return
	 */
	@Operation(summary = "API delete post", description = "Delete Post")
	@ApiResponse(responseCode = "200", description = "Delete post successfully")
	@ApiResponse(responseCode = "400", description = "Delete post error")
	@PostMapping("/delete/{id}")
	public BaseResponse<DeletePostResDto> deletePost(@PathVariable("id") Long idPost) {
		DeletePostResDto resDto = postService.delete(idPost);
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
