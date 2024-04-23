package com.example.social_network.dto.post;

import com.example.social_network.enumdef.PostType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostPostReqDto {

	private String content;

	private int privacy = PostType.PUBLIC.getCode();
}
