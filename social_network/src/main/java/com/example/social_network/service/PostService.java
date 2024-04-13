package com.example.social_network.service;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.example.social_network.dto.post.DeletePostResDto;
import com.example.social_network.dto.post.PostListReqDto;
import com.example.social_network.dto.post.PostPostReqDto;
import com.example.social_network.dto.post.PostPostResDto;
import com.example.social_network.dto.post.PostPrivacyPutReqDto;
import com.example.social_network.dto.post.PostPutReqDto;

public interface PostService {

	PostPostResDto createPost(PostPostReqDto reqDto, MultipartFile[] files);

	PostPostResDto update(PostPutReqDto reqDto, MultipartFile[] files);

	DeletePostResDto delete(Long id);

	PostPostResDto getPostDetail(Long Id);

	Page<PostPostResDto> getUserAllPost(Long id, PostListReqDto reqDto);

	Page<PostPostResDto> getAllPostByKeyword(PostListReqDto reqDto);

	PostPostResDto updatePrivacy(PostPrivacyPutReqDto reqDto);
}
