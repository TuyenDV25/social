package com.example.social_network.service;

public interface LikeService {

	void likePost(Long postId);
	
	void likeComment(Long commentId);

}
