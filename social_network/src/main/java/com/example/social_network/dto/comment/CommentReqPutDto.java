package com.example.social_network.dto.comment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentReqPutDto {

	private String content;

	private Long deleteImageId;
}
