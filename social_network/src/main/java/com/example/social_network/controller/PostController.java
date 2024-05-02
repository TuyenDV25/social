package com.example.social_network.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.social_network.dto.post.DeletePostResDto;
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
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/post")
public class PostController {

	private final PostService postService;

	/**
	 * create post
	 * 
	 * @param reqDto {@link PostPostReqDto}
	 * @return {@link PostPostResDto}
	 */
	@PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	@Operation(summary = "API create post", description = "create a post")
	@ApiResponse(responseCode = "200", description = "create post successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostPostReqDto.class)))
	@ApiResponse(responseCode = "400", description = "create post error")
	@ApiResponse(responseCode = "401", description = "UserName not found")
	BaseResponse<PostPostResDto> createPost(
			@RequestPart(name = "reqDto", required = false) @Valid PostPostReqDto reqDto,
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
	@PutMapping(value = "/{id}",consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	@Operation(summary = "API update post", description = "Update Post")
	@ApiResponse(responseCode = "200", description = "Update post successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostPutReqDto.class)))
	@ApiResponse(responseCode = "400", description = "Update post error")
	@ApiResponse(responseCode = "401", description = "UserName not found")
	@ApiResponse(responseCode = "404", description = "Update post not existed")
	BaseResponse<PostPostResDto> updatePost(@PathVariable("id") Long idPost, @RequestPart @Valid PostPutReqDto reqDto,
			@RequestPart(required = false) MultipartFile[] multipartFile) {
		PostPostResDto resDto = postService.update(idPost, reqDto, multipartFile);
		return BaseResponse.<PostPostResDto>builder().result(resDto).message(CommonConstants.POST_UPDATE_SUCCESS)
				.build();
	}

	/**
	 * update privacy of the post
	 * 
	 * @param idPost
	 * @param reqDto
	 * @return {@link PostPostResDto}
	 */
	@PatchMapping(value = "/update-privacy/{idPost}")
	@Operation(summary = "API update privacy", description = "Update privacy of post")
	@ApiResponse(responseCode = "200", description = "Update privacy successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostPrivacyPutReqDto.class)))
	@ApiResponse(responseCode = "401", description = "UserName not found")
	@ApiResponse(responseCode = "404", description = "Update post not existed")
	@ApiResponse(responseCode = "400", description = "Update post error")
	BaseResponse<PostPostResDto> updatePrivacy(@PathVariable("idPost") Long idPost, @Valid @RequestBody PostPrivacyPutReqDto reqDto) {
		PostPostResDto resDto = postService.updatePrivacy(idPost, reqDto);
		return BaseResponse.<PostPostResDto>builder().result(resDto)
				.message(CommonConstants.POST_UPDATE_PRIVACY_SUCCESS).build();
	}

	/**
	 * delete post
	 * 
	 * @param idPost
	 * @return {@link DeletePostResDto}
	 */
	@Operation(summary = "API delete post", description = "Delete Post")
	@ApiResponse(responseCode = "200", description = "Delete post successfully")
	@ApiResponse(responseCode = "401", description = "UserName not found")
	@ApiResponse(responseCode = "404", description = "Delete post not existed")
	@ApiResponse(responseCode = "400", description = "delete post error")
	@DeleteMapping("/{id}")
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
	@GetMapping("/{id}")
	@Operation(summary = "API Get detail post")
	@ApiResponse(responseCode = "200", description = "Get detail post successfully")
	@ApiResponse(responseCode = "401", description = "UserName not found")
	@ApiResponse(responseCode = "404", description = "Get detail post not existed")
	public BaseResponse<PostPostResDto> getPost(@PathVariable("id") Long idPost) {
		PostPostResDto resDto = postService.getPostDetail(idPost);
		return BaseResponse.<PostPostResDto>builder().result(resDto).message(CommonConstants.POST_DETAIL_SUCCESS)
				.build();
	}

	/**
	 * 
	 * @param id
	 * @param pageNumber
	 * @return list post of user
	 */
	@GetMapping("all/{id}")
	@Operation(summary = "API get list post")
	@ApiResponse(responseCode = "200", description = "Get list post successfully")
	@ApiResponse(responseCode = "401", description = "UserName not found")
	@ApiResponse(responseCode = "404", description = "Get post of user not existed")
	public BaseResponse<PostListResDto> getUserAllPost(@PathVariable("id") Long id,
			@RequestParam Integer pageNumber) {
		Page<PostPostResDto> result = postService.getUserAllPost(id, pageNumber);
		return BaseResponse.<PostListResDto>builder().result(PostListResDto.builder().listPost(result).build())
				.message(CommonConstants.USER_SEARCH_SUCCES).build();
	}

	/**
	 * get list post contain content
	 * 
	 * @param pageNumber
	 * @param searchContent
	 * @return list Post
	 */
	@GetMapping("search")
	@Operation(summary = "API get list post by name")
	@ApiResponse(responseCode = "200", description = "Get list post by Name successfully")
	@ApiResponse(responseCode = "401", description = "UserName not found")
	public BaseResponse<PostListResDto> getAllPostByKeyword(@RequestParam Integer pageNumber, @RequestParam String searchContent) {
		Page<PostPostResDto> result = postService.getAllPostByKeyword(pageNumber, searchContent);
		return BaseResponse.<PostListResDto>builder().result(PostListResDto.builder().listPost(result).build())
				.message(CommonConstants.USER_SEARCH_SUCCES).build();
	}

}
