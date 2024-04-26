package com.example.social_network.dto.post;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.example.social_network.enumdef.PostType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostPutReqDto {

	@Length(max = 500, message = "must be smaller than 500 characters!")
	private String content;

	private List<Long> listImageIdDeletes;

	private int privacy = PostType.PUBLIC.getCode();
}
