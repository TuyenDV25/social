package com.example.social_network.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.social_network.dto.post.PostPostReqDto;
import com.example.social_network.dto.post.PostPostResDto;
import com.example.social_network.dto.utils.post.PostResponseUtils;
import com.example.social_network.entity.Post;
import com.example.social_network.entity.UserInfo;
import com.example.social_network.exception.AppException;
import com.example.social_network.exception.ErrorCode;
import com.example.social_network.mapper.post.PostRequestMapper;
import com.example.social_network.repository.PostRepository;
import com.example.social_network.repository.UserInfoRepository;
import com.example.social_network.service.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	PostRequestMapper imageMapper;

	@Autowired
	UserInfoRepository userInfoRepository;

	@Autowired
	PostRepository postRepository;

	@Autowired
	PostResponseUtils postResponseUtils;

	@Override
	public PostPostResDto save(PostPostReqDto reqDto) {

		Post post = imageMapper.dtoToEntity(reqDto);

		post.setUserInfo(userInfoRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
				.get());
		postRepository.save(post);

		return postResponseUtils.convert(post);
	}

	@Override
	public PostPostResDto update(Long id, PostPostReqDto reqDto) {
		Post post = postRepository.findById(id).get();
		if (post == null) {
			throw new AppException(ErrorCode.POST_NOTEXISTED);
		}
		UserInfo userInfor = userInfoRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
				.get();
		if (postRepository.findByUserInfoAndId(userInfor, id) == null) {
			throw new AppException(ErrorCode.UNAUTHORIZED);
		}
		
		post.setContent(reqDto.getContent());
		
		return null;
	}

	@Override
	public void delete(Long id) {

	}

}
