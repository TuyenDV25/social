package com.example.social_network.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostListReqDto {
	private int pageNo;

	private int pageSize;

	private String content;
}
