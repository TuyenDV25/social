package com.example.social_network.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentListReqDto {
	private int pageNo;

	private int pageSize;
}
