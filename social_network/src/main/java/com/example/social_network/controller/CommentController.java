package com.example.social_network.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.social_network.dto.comment.CommentListResDto;
import com.example.social_network.dto.comment.CommentReqPostDto;
import com.example.social_network.dto.comment.CommentReqPutDto;
import com.example.social_network.dto.comment.CommentResDto;
import com.example.social_network.response.BaseResponse;
import com.example.social_network.service.CommentService;
import com.example.social_network.utils.CommonConstants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/comment")
public class CommentController {

	@Autowired
	private CommentService commentService;

	/**
	 * create comment
	 * 
	 * @param reqDto {@link CommentReqPostDto}
	 * @param idPost
	 * @return {@link CommentResDto}
	 */
	@PostMapping(value = "/create", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	@Operation(summary = "API comment")
	@ApiResponse(responseCode = "200", description = "create comment successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentResDto.class)))
	@ApiResponse(responseCode = "400", description = "create comment error")
	public BaseResponse<CommentResDto> createComment(@RequestPart @Valid CommentReqPostDto reqDto,
			@RequestPart(required = false) MultipartFile multipartFile) {
		CommentResDto resDto = commentService.createComment(reqDto, multipartFile);
		return BaseResponse.<CommentResDto>builder().result(resDto).message(CommonConstants.COMMENT_CREATE_SUCCESS)
				.build();
	}

	@PutMapping(value = "/update", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	@Operation(summary = "API update comment", description = "Update a comment")
	@ApiResponse(responseCode = "200", description = "Update comment successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentResDto.class)))
	@ApiResponse(responseCode = "400", description = "Update comment error")
	public BaseResponse<CommentResDto> updateComment(@RequestPart @Valid CommentReqPutDto reqDto,
			@RequestPart(required = false) MultipartFile multipartFile) {
		CommentResDto resDto = commentService.updateComment(reqDto, multipartFile);
		return BaseResponse.<CommentResDto>builder().result(resDto).message(CommonConstants.COMMENT_UPDATE_SUCCESS)
				.build();
	}

	@DeleteMapping("/delete/{id}")
	@Operation(summary = "API delete comment", description = "Delete a comment")
	@ApiResponse(responseCode = "200", description = "Delete comment successfully")
	@ApiResponse(responseCode = "400", description = "Delete comment error")
	public BaseResponse<?> deletePost(@PathVariable("id") Long idComment) {
		commentService.delete(idComment);
		return BaseResponse.builder().message(CommonConstants.COMMENT_DELETE_SUCCESS).build();
	}

	@GetMapping("all/{id}")
	@Operation(summary = "API get all comment of a post", description = "get list comment")
	@ApiResponse(responseCode = "200", description = "get comment successfully")
	@ApiResponse(responseCode = "400", description = "get comment error")
	public BaseResponse<CommentListResDto> getAllComment(@PathVariable("id") Long id,
			@RequestParam Integer pageNumber) {
		Page<CommentResDto> result = commentService.getAllComment(id, pageNumber);
		return BaseResponse.<CommentListResDto>builder().result(CommentListResDto.builder().listComment(result).build())
				.message(CommonConstants.USER_SEARCH_SUCCES).build();
	}

}
