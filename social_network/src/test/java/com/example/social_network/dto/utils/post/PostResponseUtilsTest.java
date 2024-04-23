package com.example.social_network.dto.utils.post;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.social_network.dto.utils.comment.CommentResponseUtils;
import com.example.social_network.dto.utils.like.LikeResponseUtils;
import com.example.social_network.dto.utils.user.UserInfoResponseUtils;
import com.example.social_network.entity.Post;
import com.example.social_network.mapper.image.ImageResponseMapper;
import com.example.social_network.mapper.post.PostResponseMapper;
import com.example.social_network.repository.CommentRepository;
import com.example.social_network.repository.LikeRepository;

@ExtendWith(MockitoExtension.class)
public class PostResponseUtilsTest {

	@Mock
	private PostResponseMapper postMapper;
	
	@Mock
	private ImageResponseMapper imageMapper;

	@Mock
	private LikeRepository likeRepository;

	@Mock
	private CommentRepository commentRepository;

	@Mock
	private CommentResponseUtils commentResponseUtils;

	@Mock
	private LikeResponseUtils likeResponseUtils;

	@Mock
	private UserInfoResponseUtils userResponseUtils;
	
	private PostResponseUtils postResponseUtils;
	
	@BeforeEach
	public void setUp() {
		postResponseUtils = new PostResponseUtils(postMapper, imageMapper, likeRepository,
				commentRepository, commentResponseUtils, likeResponseUtils, userResponseUtils);
	}
	
	@Test
	void convert_validRequest_success() {
		Post post = new Post();
	}
}
