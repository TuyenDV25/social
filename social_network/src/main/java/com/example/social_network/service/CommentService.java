package com.example.social_network.service;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.example.social_network.dto.comment.CommentListReqDto;
import com.example.social_network.dto.comment.CommentReqPostDto;
import com.example.social_network.dto.comment.CommentReqPutDto;
import com.example.social_network.dto.comment.CommentResDto;

public interface CommentService {

	CommentResDto createComment(CommentReqPostDto reqDto, MultipartFile[] files);

	CommentResDto updateComment(CommentReqPutDto reqDto, MultipartFile[] files);

	void delete(Long id);
	
	Page<CommentResDto> getAllComment(Long id, CommentListReqDto reqDto);

}
