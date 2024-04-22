package com.example.social_network.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.social_network.dto.post.PostPostResDto;
import com.example.social_network.dto.utils.post.PostResponseUtils;
import com.example.social_network.entity.Post;
import com.example.social_network.entity.UserInfo;
import com.example.social_network.enumdef.PostType;
import com.example.social_network.repository.PostRepository;
import com.example.social_network.repository.UserInfoRepository;
import com.example.social_network.service.TimeLikeService;
import com.example.social_network.utils.CommonConstants;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TimeLikeServiceImpl implements TimeLikeService {

	private final PostRepository postRepository;

	private final UserInfoRepository userInfoRepository;

	private final PostResponseUtils postResponseUtils;

	@Override
	public Page<PostPostResDto> getTimeLinePost(Pageable page) {

		UserInfo user = userInfoRepository
				.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
				.orElseThrow(() -> new UsernameNotFoundException(CommonConstants.USER_NOT_FOUND));
		
		Page<Post> pagedResult = postRepository.findByUserInfo(user, page);

		List<PostPostResDto> postResponseList = pagedResult.stream().filter(
				post -> post.getUserInfo().getId() == user.getId() || (post.getUserInfo().getId() != user.getId()
						&& post.getPrivacy() != PostType.ONLY_ME.getCode()))
				.map(postResponseUtils::convert).collect(Collectors.toList());
		return new PageImpl<>(postResponseList, page, postResponseList.size());
	}

}
