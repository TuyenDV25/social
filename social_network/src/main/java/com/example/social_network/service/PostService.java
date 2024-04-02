package com.example.social_network.service;

import com.example.social_network.dto.post.PostPostReqDto;
import com.example.social_network.dto.post.PostPostResDto;


public interface PostService {

	PostPostResDto save(PostPostReqDto reqDto);

	PostPostResDto update(Long id, PostPostReqDto reqDto);

	void delete(Long id);

}
