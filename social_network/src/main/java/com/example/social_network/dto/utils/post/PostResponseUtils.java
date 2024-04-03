package com.example.social_network.dto.utils.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.social_network.dto.post.PostPostResDto;
import com.example.social_network.dto.utils.ResponseUtilsAdapter;
import com.example.social_network.entity.Post;
import com.example.social_network.mapper.image.ImageResponseMapper;
import com.example.social_network.mapper.post.PostResponseMapper;
import com.example.social_network.repository.CommentRepository;
import com.example.social_network.repository.LikeRepository;
import com.example.social_network.repository.ShareRepository;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostResponseUtils extends ResponseUtilsAdapter<Post, PostPostResDto> {

	@Autowired
	PostResponseMapper postMapper;

	@Autowired
	ImageResponseMapper imageMapper;

	@Autowired
	LikeRepository likeRepository;

	@Autowired
	CommentRepository commentRepository;

	@Autowired
	ShareRepository shareRepository;

	@Override
	public PostPostResDto convert(Post entity) {
		PostPostResDto resDto = postMapper.entityToDto(entity);
		resDto.setImage(imageMapper.entityToDto(entity.getImage()));
		resDto.setLikeCount(likeRepository.countByPostAndStatus(entity, true));
		resDto.setCommentCount(commentRepository.countByPost(entity));
		resDto.setShareCount(shareRepository.countByPost(entity));
		return resDto;
	}

}
