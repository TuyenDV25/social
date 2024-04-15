package com.example.social_network.service;

import com.example.social_network.dto.comment.LikeCommentReqDto;
import com.example.social_network.dto.post.LikePostReqDto;

public interface LikeService {

	void likePost(LikePostReqDto reqDto);
	
	void likeComment(LikeCommentReqDto reqDto);

}
