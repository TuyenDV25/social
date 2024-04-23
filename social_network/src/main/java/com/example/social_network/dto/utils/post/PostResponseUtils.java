package com.example.social_network.dto.utils.post;

import org.springframework.stereotype.Component;

import com.example.social_network.dto.post.PostPostResDto;
import com.example.social_network.dto.utils.ResponseUtilsAdapter;
import com.example.social_network.dto.utils.comment.CommentResponseUtils;
import com.example.social_network.dto.utils.like.LikeResponseUtils;
import com.example.social_network.dto.utils.user.UserInfoResponseUtils;
import com.example.social_network.entity.Post;
import com.example.social_network.mapper.image.ImageResponseMapper;
import com.example.social_network.mapper.post.PostResponseMapper;
import com.example.social_network.repository.CommentRepository;
import com.example.social_network.repository.LikeRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PostResponseUtils extends ResponseUtilsAdapter<Post, PostPostResDto> {

	private final PostResponseMapper postMapper;

	private final ImageResponseMapper imageMapper;

	private final LikeRepository likeRepository;

	private final CommentRepository commentRepository;

	private final CommentResponseUtils commentResponseUtils;

	private final LikeResponseUtils likeResponseUtils;

	private final UserInfoResponseUtils userResponseUtils;

	@Override
	public PostPostResDto convert(Post entity) {
		PostPostResDto resDto = postMapper.entityToDto(entity);
		resDto.setImage(imageMapper.dtoListToEntityList(entity.getImages()));
		resDto.setLikeCount(likeRepository.countByPost(entity));
		resDto.setCommentCount(commentRepository.countByPost(entity));
		resDto.setComments(commentResponseUtils.convert(entity.getComments()));
		resDto.setLikes(likeResponseUtils.convert(entity.getLikes()));
		resDto.setUserInfo(userResponseUtils.convert(entity.getUserInfo()));
		return resDto;
	}

}
