package com.example.social_network.dto.post;

import org.springframework.data.domain.Page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class PostListResDto {

	private Page<PostPostResDto> listPost;
}
