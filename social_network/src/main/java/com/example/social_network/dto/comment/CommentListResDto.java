package com.example.social_network.dto.comment;

import org.springframework.data.domain.Page;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CommentListResDto {

	private Page<CommentResDto> listComment;

}
