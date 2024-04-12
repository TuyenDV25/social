package com.example.social_network.service;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.example.social_network.dto.post.DeletePostReqDto;
import com.example.social_network.dto.post.DeletePostResDto;
import com.example.social_network.dto.post.PostListReqDto;
import com.example.social_network.dto.post.PostPostReqDto;
import com.example.social_network.dto.post.PostPostResDto;
import com.example.social_network.dto.post.PostPutReqDto;

public interface PostService {

	PostPostResDto createPost(PostPostReqDto reqDto, MultipartFile[] files);

	PostPostResDto update(PostPostReqDto reqDto, MultipartFile[] files);

	DeletePostResDto delete(DeletePostReqDto reqDto);

	PostPostResDto getPostDetail(Long Id);

	Page<PostPostResDto> getUserAllPost(Long id, PostListReqDto reqDto);

	Page<PostPostResDto> getAllPostByKeyword(PostListReqDto reqDto);

	PostPostResDto updatePrivacy(PostPutReqDto reqDto);
}
