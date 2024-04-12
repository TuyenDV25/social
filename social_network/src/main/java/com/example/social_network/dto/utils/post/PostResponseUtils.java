package com.example.social_network.dto.utils.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.social_network.dto.post.PostPostResDto;
import com.example.social_network.dto.utils.ResponseUtilsAdapter;
import com.example.social_network.dto.utils.comment.CommentResponseUtils;
import com.example.social_network.dto.utils.like.LikeResponseUtils;
import com.example.social_network.entity.Post;
import com.example.social_network.mapper.image.ImageResponseMapper;
import com.example.social_network.mapper.post.PostResponseMapper;
import com.example.social_network.repository.CommentRepository;
import com.example.social_network.repository.LikeRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PostResponseUtils extends ResponseUtilsAdapter<Post, PostPostResDto> {

	@Autowired
	private PostResponseMapper postMapper;

	@Autowired
	private ImageResponseMapper imageMapper;

	@Autowired
	private LikeRepository likeRepository;

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private CommentResponseUtils commentResponseUtils;

	@Autowired
	private LikeResponseUtils likeResponseUtils;

	@Override
	public PostPostResDto convert(Post entity) {
		PostPostResDto resDto = postMapper.entityToDto(entity);
		resDto.setImage(imageMapper.dtoListToEntityList(entity.getImages()));
		resDto.setLikeCount(likeRepository.countByPost(entity));
		resDto.setCommentCount(commentRepository.countByPost(entity));
		resDto.setComments(commentResponseUtils.convert(entity.getComments()));
		resDto.setLikes(likeResponseUtils.convert(entity.getLikes()));
		return resDto;
	}

}
