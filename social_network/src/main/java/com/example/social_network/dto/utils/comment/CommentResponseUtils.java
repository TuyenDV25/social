package com.example.social_network.dto.utils.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.social_network.dto.comment.CommentResDto;
import com.example.social_network.dto.utils.ResponseUtilsAdapter;
import com.example.social_network.dto.utils.user.UserInfoResponseUtils;
import com.example.social_network.entity.Comment;
import com.example.social_network.mapper.image.ImageResponseMapper;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentResponseUtils extends ResponseUtilsAdapter<Comment, CommentResDto> {

	@Autowired
	ImageResponseMapper imageMapper;

	@Autowired
	UserInfoResponseUtils userInfoResponseUtils;

	@Override
	public CommentResDto convert(Comment entity) {
		CommentResDto resDto = new CommentResDto();
		resDto.setContent(entity.getContent());
		resDto.setImage(imageMapper.entityToDto(entity.getImage()));
		resDto.setUserInfo(userInfoResponseUtils.convert(entity.getUser()));
		return resDto;
	}

}
