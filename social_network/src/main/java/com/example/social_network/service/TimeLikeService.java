package com.example.social_network.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.social_network.dto.post.PostPostResDto;

public interface TimeLikeService {
	
	Page<PostPostResDto> getTimeLinePost(Pageable page);
}
