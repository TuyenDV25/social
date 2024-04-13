package com.example.social_network.service.impl;

import java.util.ArrayList;
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
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.social_network.dto.image.ImageResDto;
import com.example.social_network.dto.post.DeletePostResDto;
import com.example.social_network.dto.post.PostListReqDto;
import com.example.social_network.dto.post.PostPostReqDto;
import com.example.social_network.dto.post.PostPostResDto;
import com.example.social_network.dto.post.PostPrivacyPutReqDto;
import com.example.social_network.dto.post.PostPutReqDto;
import com.example.social_network.dto.utils.post.PostResponseUtils;
import com.example.social_network.entity.Image;
import com.example.social_network.entity.Post;
import com.example.social_network.entity.UserInfo;
import com.example.social_network.enumdef.PostType;
import com.example.social_network.exception.AppException;
import com.example.social_network.exception.ErrorCode;
import com.example.social_network.mapper.image.ImageResponseMapper;
import com.example.social_network.mapper.post.PostRequestMapper;
import com.example.social_network.repository.PostRepository;
import com.example.social_network.repository.UserInfoRepository;
import com.example.social_network.service.FileService;
import com.example.social_network.service.ImageService;
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
	ImageService imageService;

	@Autowired
	UserInfoRepository userInfoRepository;

	@Autowired
	PostRepository postRepository;

	@Autowired
	PostResponseUtils postResponseUtils;

	@Override
	public PostPostResDto createPost(PostPostReqDto reqDto, MultipartFile[] files) {

		if (StringUtils.isBlank(reqDto.getContent()) && (files == null || files.length < 1)) {
			throw new AppException(ErrorCode.POST_UPLOAD_WRONG);
		}

		Post post = postMapper.dtoToEntity(reqDto);

		post.setUserInfo(userInfoRepository
				.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).get());

		Post insertedPost = postRepository.save(post);
		List<Image> imageList = new ArrayList<>();
		if (files != null && files.length > 0) {
			List<ImageResDto> imageResDto = fileService.uploadImage(files);

			if (!CollectionUtils.isEmpty(imageResDto)) {
				imageResDto.stream().forEach(imageDto -> {
					Image imageE = imageService.findOneById(imageDto.getId());
					imageE.setPost(post);
					imageService.save(imageE);
					imageList.add(imageE);

				});
			}
		}
		insertedPost.setImages(imageList);

		return postResponseUtils.convert(insertedPost);
	}

	@Override
	public PostPostResDto update(PostPutReqDto reqDto, MultipartFile[] files) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		UserInfo user = userInfoRepository.findByUsername(authentication.getName())
				.orElseThrow(() -> new UsernameNotFoundException(CommonConstants.USER_NOT_FOUND));
		Post post = postRepository.findById(reqDto.getId()).get();
		if (post == null) {
			throw new AppException(ErrorCode.POST_NOTEXISTED);
		}
		if (postRepository.findByUserInfoAndId(user, post.getId()) == null) {
			throw new AppException(ErrorCode.POST_NOTEXISTED);
		}

		post.setContent(reqDto.getContent());
		post.setPrivacy(reqDto.getPrivacy());

		
		if (StringUtils.isBlank(post.getContent())
				&& ((post.getImages() == null || post.getImages().size() < 1) && (files == null || files.length < 1))) {
			throw new AppException(ErrorCode.POST_UPLOAD_WRONG);
		}
		
		//delete image from post
		List<Image> removeImageList = new ArrayList<>();
		if (!CollectionUtils.isEmpty(reqDto.getListImageIdDeletes())) {
			reqDto.getListImageIdDeletes().stream().forEach(id -> {
				imageService.deleteById(id);
				removeImageList.add(imageService.findOneById(id));
			});
		}

		Post insertedPost = postRepository.save(post);
		
		List<Image> imageList = new ArrayList<>();

		//upload image to cloudinary
		if (files != null && files.length > 0) {
			List<ImageResDto> imageResDto = fileService.uploadImage(files);
			if (!CollectionUtils.isEmpty(imageResDto)) {
				imageResDto.stream().forEach(imageDto -> {
					Image imageE = imageService.findOneById(imageDto.getId());
					imageE.setPost(post);
					imageService.save(imageE);
					imageList.add(imageE);
				});
			}
		}
		insertedPost.getImages().addAll(imageList);
		insertedPost.getImages().removeAll(removeImageList);
		return postResponseUtils.convert(insertedPost);
	}

	@Override
	public DeletePostResDto delete(Long id) {
		if (postRepository.findOneById(id) == null)
			throw new AppException(ErrorCode.POST_NOTEXISTED);

		UserInfo userInfor = userInfoRepository
				.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).get();
		if (postRepository.findByUserInfoAndId(userInfor, id) == null) {
			throw new AppException(ErrorCode.UNAUTHORIZED);
		}
		postRepository.deleteById(id);

		return new DeletePostResDto(id);
	}

	@Override
	public PostPostResDto getPostDetail(Long Id) {
		Post post = postRepository.findOneById(Id);
		if (post == null) {
			throw new AppException(ErrorCode.POST_NOTEXISTED);
		}
		UserInfo userInfor = userInfoRepository
				.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).get();
		if (post.getPrivacy() == PostType.ONLY_ME.getCode() && userInfor.getId() != post.getId()) {
			throw new AppException(ErrorCode.POST_NOTEXISTED);
		}
		PostPostResDto resDto = postResponseUtils.convert(post);
		return resDto;
	}

	@Override
	public Page<PostPostResDto> getUserAllPost(Long id, PostListReqDto reqDto) {
		Pageable paging = PageRequest.of(reqDto.getPageNo(), 10);
		UserInfo user = userInfoRepository.findOneById(id);
		if (user == null) {
			throw new AppException(ErrorCode.USER_NOT_EXISTED);
		}
		Page<Post> pagedResult = postRepository.findByUserInfo(user, paging);
		UserInfo userInfor = userInfoRepository
				.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).get();
		List<PostPostResDto> postResponseList = pagedResult.stream()
				.filter(post -> post.getUserInfo().getId() == userInfor.getId()
						|| (post.getUserInfo().getId() != userInfor.getId()
								&& post.getPrivacy() != PostType.ONLY_ME.getCode()))
				.map(postResponseUtils::convert).collect(Collectors.toList());
		return new PageImpl<>(postResponseList, paging, postResponseList.size());
	}

	@Override
	public Page<PostPostResDto> getAllPostByKeyword(PostListReqDto reqDto) {
		Pageable paging = PageRequest.of(reqDto.getPageNo(), 10);
		Page<Post> pagedResult = postRepository.findByContentContains(reqDto.getContent(), paging);
		UserInfo userInfor = userInfoRepository
				.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).get();
		List<PostPostResDto> postResponseList = pagedResult.stream()
				.filter(post -> post.getUserInfo().getId() == userInfor.getId()
						|| (post.getUserInfo().getId() != userInfor.getId()
								&& post.getPrivacy() != PostType.ONLY_ME.getCode()))
				.map(postResponseUtils::convert).collect(Collectors.toList());
		return new PageImpl<>(postResponseList, paging, postResponseList.size());
	}

	@Override
	public PostPostResDto updatePrivacy(PostPrivacyPutReqDto reqDto) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		UserInfo user = userInfoRepository.findByUsername(authentication.getName())
				.orElseThrow(() -> new UsernameNotFoundException(CommonConstants.USER_NOT_FOUND));
		Post post = postRepository.findByUserInfoAndId(user, reqDto.getPostId());
		if (post == null) {
			throw new AppException(ErrorCode.POST_NOTEXISTED);
		}
		post.setPrivacy(reqDto.getPrivacy());
		postRepository.save(post);

		return postResponseUtils.convert(post);
	}

}
