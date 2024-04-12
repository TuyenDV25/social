package com.example.social_network.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.social_network.dto.comment.CommentListReqDto;
import com.example.social_network.dto.comment.CommentListResDto;
import com.example.social_network.dto.comment.CommentReqPostDto;
import com.example.social_network.dto.comment.CommentReqPutDto;
import com.example.social_network.dto.comment.CommentResDto;
import com.example.social_network.response.BaseResponse;
import com.example.social_network.service.CommentService;
import com.example.social_network.utils.CommonConstants;

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
	@PostMapping(value = "/create/", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public BaseResponse<CommentResDto> createComment(@RequestPart @Valid CommentReqPostDto reqDto, @RequestPart(required = false) MultipartFile[] multipartFile) {
		CommentResDto resDto = commentService.createComment(reqDto, multipartFile);
		return BaseResponse.<CommentResDto>builder().result(resDto).message(CommonConstants.COMMENT_CREATE_SUCCESS)
				.build();
	}

	@PutMapping(value = "/update", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public BaseResponse<CommentResDto> updateComment(@RequestPart @Valid CommentReqPutDto reqDto, @RequestPart(required = false) MultipartFile[] multipartFile) {
		CommentResDto resDto = commentService.updateComment(reqDto, multipartFile);
		return BaseResponse.<CommentResDto>builder().result(resDto).message(CommonConstants.COMMENT_UPDATE_SUCCESS)
				.build();
	}

	@PostMapping("/delete/{id}")
	public BaseResponse<?> deletePost(@PathVariable("id") Long idComment) {
		commentService.delete(idComment);
		return BaseResponse.builder().result(null).message(CommonConstants.COMMENT_DELETE_SUCCESS).build();
	}

	@PostMapping("all/{id}")
	public BaseResponse<CommentListResDto> getAllComment(@PathVariable("id") Long id,
			@Valid @RequestBody CommentListReqDto reqDto) {
		Page<CommentResDto> result = commentService.getAllComment(id, reqDto);
		return BaseResponse.<CommentListResDto>builder().result(CommentListResDto.builder().listComment(result).build())
				.message(CommonConstants.USER_SEARCH_SUCCES).build();
	}

}
