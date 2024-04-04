package com.example.social_network.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.social_network.dto.image.ImageResDto;
import com.example.social_network.dto.post.DeletePostReqDto;
import com.example.social_network.dto.post.DeletePostResDto;
import com.example.social_network.dto.post.PostListReqDto;
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
	public PostPostResDto update(Long id, PostPostReqDto reqDto) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		UserInfo user = userInfoRepository.findByUsername(authentication.getName())
				.orElseThrow(() -> new UsernameNotFoundException(CommonConstants.USER_NOT_FOUND));
		Post post = postRepository.findById(id).get();
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
		post.setPrivacy(reqDto.getPrivacy());

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

	@Override
	public PostPostResDto getPostDetail(Long Id) {
		Post post = postRepository.findOneById(Id);
		if (post == null) {
			throw new AppException(ErrorCode.POST_NOTEXISTED);
		}
		UserInfo userInfor = userInfoRepository
				.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).get();
		if (post.getPrivacy() == CommonConstants.PRIVACY_ONLYME && userInfor.getId() != post.getId()) {
			throw new AppException(ErrorCode.POST_NOTEXISTED);
		}
		PostPostResDto resDto = postResponseUtils.convert(post);
		return resDto;
	}

	@Override
	public Page<PostPostResDto> getUserAllPost(Long id, PostListReqDto reqDto) {
		Pageable paging = PageRequest.of(reqDto.getPageNo(), reqDto.getPageSize());
		UserInfo user = userInfoRepository.findOneById(id);
		if (user == null) {
			throw new AppException(ErrorCode.USER_NOT_EXISTED);
		}
		Page<Post> pagedResult = postRepository.findAllByUserInfoOrderbyIdDesc(user, paging);
		UserInfo userInfor = userInfoRepository
				.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).get();
		List<PostPostResDto> postResponseList = pagedResult.stream()
				.filter(post -> post.getUserInfo().getId() == userInfor.getId()
						|| (post.getUserInfo().getId() != userInfor.getId()
								&& post.getPrivacy() != CommonConstants.PRIVACY_ONLYME))
				.map(postResponseUtils::convert).collect(Collectors.toList());
		return new PageImpl<>(postResponseList, paging, postResponseList.size());
	}

	@Override
	public Page<PostPostResDto> getAllPostByKeyword(PostListReqDto reqDto) {
		Pageable paging = PageRequest.of(reqDto.getPageNo(), reqDto.getPageSize());
		Page<Post> pagedResult = postRepository.findAllByContentContains(reqDto.getContent(),paging);
		UserInfo userInfor = userInfoRepository
				.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).get();
		List<PostPostResDto> postResponseList = pagedResult.stream()
				.filter(post -> post.getUserInfo().getId() == userInfor.getId()
						|| (post.getUserInfo().getId() != userInfor.getId()
								&& post.getPrivacy() != CommonConstants.PRIVACY_ONLYME))
				.map(postResponseUtils::convert).collect(Collectors.toList());
		return new PageImpl<>(postResponseList, paging, postResponseList.size());
	}

}
