package com.example.social_network.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.social_network.dto.image.ImageResDto;
import com.example.social_network.dto.post.DeletePostReqDto;
import com.example.social_network.dto.post.DeletePostResDto;
import com.example.social_network.dto.post.PostPostReqDto;
import com.example.social_network.dto.post.PostPostResDto;
import com.example.social_network.dto.utils.post.PostResponseUtils;
import com.example.social_network.entity.Post;
import com.example.social_network.entity.UserInfo;
import com.example.social_network.exception.AppException;
import com.example.social_network.exception.ErrorCode;
import com.example.social_network.mapper.image.ImageResponseMapper;
import com.example.social_network.mapper.post.PostRequestMapper;
import com.example.social_network.repository.PostRepository;
import com.example.social_network.repository.UserInfoRepository;
import com.example.social_network.service.FileService;
import com.example.social_network.service.PostService;
import com.example.social_network.utils.CommonConstants;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	PostRequestMapper postMapper;

	@Autowired
	ImageResponseMapper imageMapper;

	@Autowired
	FileService fileService;

	@Autowired
	UserInfoRepository userInfoRepository;

	@Autowired
	PostRepository postRepository;

	@Autowired
	PostResponseUtils postResponseUtils;

	@Override
	public PostPostResDto createPost(PostPostReqDto reqDto) {

		if (StringUtils.isBlank(reqDto.getContent()) && reqDto.getUploadFile() == null) {
			throw new AppException(ErrorCode.POST_UPLOAD_WRONG);
		}

		Post post = postMapper.dtoToEntity(reqDto);

		if (reqDto.getUploadFile() != null) {
			ImageResDto imageResDto = fileService.uploadImage(reqDto.getUploadFile());

			post.setImage(imageMapper.dtoToEntity(imageResDto));
		}

		post.setUserInfo(userInfoRepository
				.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).get());
		postRepository.save(post);

		return postResponseUtils.convert(post);
	}

	@Override
	public PostPostResDto update(PostPostReqDto reqDto) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		UserInfo user = userInfoRepository.findByUsername(authentication.getName())
				.orElseThrow(() -> new UsernameNotFoundException(CommonConstants.USER_NOT_FOUND));
		Post post = postRepository.findById(user.getId()).get();
		if (post == null) {
			throw new AppException(ErrorCode.POST_NOTEXISTED);
		}
		UserInfo userInfor = userInfoRepository
				.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).get();
		if (postRepository.findByUserInfoAndId(userInfor, user.getId()) == null) {
			throw new AppException(ErrorCode.UNAUTHORIZED);
		}

		if (reqDto.getUploadFile() != null) {
			ImageResDto imageResDto = fileService.uploadImage(reqDto.getUploadFile());

			post.setImage(imageMapper.dtoToEntity(imageResDto));
		}

		post.setContent(reqDto.getContent());

		if (StringUtils.isBlank(post.getContent()) && post.getImage() == null) {
			throw new AppException(ErrorCode.POST_UPLOAD_WRONG);
		}

		postRepository.save(post);

		return postResponseUtils.convert(post);
	}

	@Override
	public DeletePostResDto delete(DeletePostReqDto reqDto) {
		if (postRepository.findOneById(reqDto.getId()) == null)
			throw new AppException(ErrorCode.POST_NOTEXISTED);

		UserInfo userInfor = userInfoRepository
				.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).get();
		if (postRepository.findByUserInfoAndId(userInfor, reqDto.getId()) == null) {
			throw new AppException(ErrorCode.UNAUTHORIZED);
		}
		postRepository.deleteById(reqDto.getId());

		return new DeletePostResDto(reqDto.getId());
	}

}
