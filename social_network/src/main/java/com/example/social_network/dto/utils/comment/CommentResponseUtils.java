package com.example.social_network.dto.utils.comment;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.social_network.dto.comment.CommentResDto;
import com.example.social_network.dto.utils.ResponseUtilsAdapter;
import com.example.social_network.dto.utils.like.LikeResponseUtils;
import com.example.social_network.dto.utils.user.UserInfoResponseUtils;
import com.example.social_network.entity.Comment;
import com.example.social_network.mapper.comment.CommentResponseMapper;
import com.example.social_network.mapper.image.ImageResponseMapper;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CommentResponseUtils extends ResponseUtilsAdapter<Comment, CommentResDto> {

	@Autowired
	private ImageResponseMapper imageMapper;
	
	@Autowired
	private CommentResponseMapper commentMapper;

	@Autowired
	private UserInfoResponseUtils userInfoResponseUtils;
	
	@Autowired
	private LikeResponseUtils likeResponseUtils;

	@Override
	public CommentResDto convert(Comment entity) {
		CommentResDto resDto = commentMapper.entityToDto(entity);
		resDto.setContent(entity.getContent());
		resDto.setImage(imageMapper.entityToDto(entity.getImage()));
		resDto.setLikes(likeResponseUtils.convert(entity.getLikes()));
		resDto.setUserInfo(userInfoResponseUtils.convert(entity.getUser()));
		return resDto;
	}

	@Override
	public List<CommentResDto> convert(List<Comment> listEntity) {
		List<CommentResDto> listResDto = new ArrayList<>();
		listEntity.stream().forEach(entity -> {
			listResDto.add(CommentResDto.builder().content(entity.getContent())
					.image(imageMapper.entityToDto(entity.getImage()))
					.likes(likeResponseUtils.convert(entity.getLikes()))
					.userInfo(userInfoResponseUtils.convert(entity.getUser())).build());
		});
		return listResDto;
	}

}
