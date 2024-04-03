package com.example.social_network.service;

import com.example.social_network.dto.post.DeletePostReqDto;
import com.example.social_network.dto.post.DeletePostResDto;
import com.example.social_network.dto.post.PostPostReqDto;
import com.example.social_network.dto.post.PostPostResDto;

public interface PostService {

	PostPostResDto createPost(PostPostReqDto reqDto);

	PostPostResDto update(PostPostReqDto reqDto);

	DeletePostResDto delete(DeletePostReqDto reqDto);

}
