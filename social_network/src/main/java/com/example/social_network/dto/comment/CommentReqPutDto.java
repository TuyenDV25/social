package com.example.social_network.dto.comment;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentReqPutDto {

	@NotNull(message = "is reqired")
	private Long id;

	private String content;

	private Long deleteImageId;
}
