package com.example.social_network.dto.comment;

import org.hibernate.validator.constraints.Length;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentReqPutDto {

	@Length(max = 500, message = "must be smaller than 500 characters!")
	private String content;

	private Long deleteImageId;
}
