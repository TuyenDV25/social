package com.example.social_network.dto.post;

import com.example.social_network.dto.image.ImageResDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostPostResDto {

	String content;

	Integer privacy;

	@JsonProperty("image")
	ImageResDto image;

	@JsonProperty("like_count")
	Long likeCount;

	@JsonProperty("cmt_count")
	Long commentCount;

	@JsonProperty("share_count")
	Long shareCount;

	Boolean liked;

}
