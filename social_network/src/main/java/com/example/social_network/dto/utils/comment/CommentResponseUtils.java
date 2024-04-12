package com.example.social_network.dto.utils.comment;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.social_network.dto.comment.CommentResDto;
import com.example.social_network.dto.utils.ResponseUtilsAdapter;
import com.example.social_network.dto.utils.user.UserInfoResponseUtils;
import com.example.social_network.entity.Comment;
import com.example.social_network.mapper.image.ImageResponseMapper;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CommentResponseUtils extends ResponseUtilsAdapter<Comment, CommentResDto> {

	@Autowired
	private ImageResponseMapper imageMapper;

	@Autowired
	private UserInfoResponseUtils userInfoResponseUtils;

	@Override
	public CommentResDto convert(Comment entity) {
		CommentResDto resDto = new CommentResDto();
		resDto.setContent(entity.getContent());
		resDto.setImage(imageMapper.entityToDto(entity.getImage()));
		resDto.setUserInfo(userInfoResponseUtils.convert(entity.getUser()));
		return resDto;
	}

	@Override
	public List<CommentResDto> convert(List<Comment> listEntity) {
		List<CommentResDto> listResDto = new ArrayList<>();
		listEntity.stream().forEach(entity -> {
			listResDto.add(CommentResDto.builder().content(entity.getContent())
					.image(imageMapper.entityToDto(entity.getImage()))
					.userInfo(userInfoResponseUtils.convert(entity.getUser())).build());
		});
		return listResDto;
	}

}
