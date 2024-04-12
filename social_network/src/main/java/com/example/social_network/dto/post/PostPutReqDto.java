package com.example.social_network.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostPutReqDto {
	
	private Long postId;

	private int privacy;

}
