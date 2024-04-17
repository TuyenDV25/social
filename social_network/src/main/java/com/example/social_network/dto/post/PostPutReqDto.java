package com.example.social_network.dto.post;

import java.util.List;

import com.example.social_network.enumdef.PostType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostPutReqDto {

	private String content;

	private List<Long> listImageIdDeletes;

	private int privacy = PostType.PUBLIC.getCode();
}
