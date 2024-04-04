package com.example.social_network.service;

import org.springframework.data.domain.Page;

import com.example.social_network.dto.comment.CommentListReqDto;
import com.example.social_network.dto.comment.CommentReqPostDto;
import com.example.social_network.dto.comment.CommentReqPutDto;
import com.example.social_network.dto.comment.CommentResDto;

public interface CommentService {

	CommentResDto createComment(CommentReqPostDto reqDto, Long postId);

	CommentResDto updateComment(CommentReqPutDto reqDto);

	void delete(Long id);
	
	Page<CommentResDto> getAllComment(Long id, CommentListReqDto reqDto);

}
