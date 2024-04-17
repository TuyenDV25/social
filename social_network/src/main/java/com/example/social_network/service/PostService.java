package com.example.social_network.service;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.example.social_network.dto.post.DeletePostResDto;
import com.example.social_network.dto.post.PostPostReqDto;
import com.example.social_network.dto.post.PostPostResDto;
import com.example.social_network.dto.post.PostPrivacyPutReqDto;
import com.example.social_network.dto.post.PostPutReqDto;
import com.example.social_network.entity.Post;
import com.example.social_network.entity.UserInfo;

public interface PostService {

	PostPostResDto createPost(PostPostReqDto reqDto, MultipartFile[] files);

	PostPostResDto update(Long id, PostPutReqDto reqDto, MultipartFile[] files);

	DeletePostResDto delete(Long id);

	PostPostResDto getPostDetail(Long Id);

	Page<PostPostResDto> getUserAllPost(Long id, Integer pageNo);

	Page<PostPostResDto> getAllPostByKeyword(Integer pageNo, String searchContent);

	PostPostResDto updatePrivacy(Long postId, PostPrivacyPutReqDto reqDto);
	
	boolean checkRightAccessPost(Post post, UserInfo user);
}
