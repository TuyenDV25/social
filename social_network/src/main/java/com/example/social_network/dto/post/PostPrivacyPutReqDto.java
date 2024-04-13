package com.example.social_network.dto.post;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostPrivacyPutReqDto {
	
	@NotNull(message = "is required")
	private Long postId;

	private int privacy;

}
