package com.example.social_network.dto.comment;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CommentReqPostDto {

	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
