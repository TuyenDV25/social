package com.example.social_network.dto.utils.like;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.social_network.dto.like.LikeResDto;
import com.example.social_network.dto.utils.ResponseUtilsAdapter;
import com.example.social_network.dto.utils.user.UserInfoResponseUtils;
import com.example.social_network.entity.Like;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class LikeResponseUtils extends ResponseUtilsAdapter<Like, LikeResDto> {

	private final UserInfoResponseUtils userInfoResponseUtils;

	@Override
	public LikeResDto convert(Like obj) {
		return LikeResDto.builder().userInfo(userInfoResponseUtils.convert(obj.getUserInfo())).build();
	}

	@Override
	public List<LikeResDto> convert(List<Like> obj) {
		List<LikeResDto> userInfoResponseList = new ArrayList<>();
		obj.stream().forEach(o -> {
			userInfoResponseList
					.add(LikeResDto.builder().userInfo(userInfoResponseUtils.convert(o.getUserInfo())).build());
		});
		return userInfoResponseList;
	}

}
