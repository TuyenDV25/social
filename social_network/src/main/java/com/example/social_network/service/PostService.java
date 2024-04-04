package com.example.social_network.service;

import org.springframework.data.domain.Page;

import com.example.social_network.dto.post.DeletePostReqDto;
import com.example.social_network.dto.post.DeletePostResDto;
import com.example.social_network.dto.post.PostListReqDto;
import com.example.social_network.dto.post.PostPostReqDto;
import com.example.social_network.dto.post.PostPostResDto;

public interface PostService {

	PostPostResDto createPost(PostPostReqDto reqDto);

	PostPostResDto update(Long id, PostPostReqDto reqDto);

	DeletePostResDto delete(DeletePostReqDto reqDto);

	PostPostResDto getPostDetail(Long Id);

	Page<PostPostResDto> getUserAllPost(Long id, PostListReqDto reqDto);

	Page<PostPostResDto> getAllPostByKeyword(PostListReqDto reqDto);
}
