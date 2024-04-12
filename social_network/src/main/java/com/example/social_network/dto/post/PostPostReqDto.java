package com.example.social_network.dto.post;

import com.example.social_network.enumdef.PostType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostPostReqDto {

	Long id;

	private String content;

	private int privacy = PostType.PUBLIC.getCode();
}
