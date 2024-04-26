package com.example.social_network.dto.comment;

import org.hibernate.validator.constraints.Length;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CommentReqPostDto {

	@Length(max = 500, message = "must be smaller than 500 characters!")
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
