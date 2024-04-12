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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.social_network.dto.comment.CommentListReqDto;
import com.example.social_network.dto.comment.CommentReqPostDto;
import com.example.social_network.dto.comment.CommentReqPutDto;
import com.example.social_network.dto.comment.CommentResDto;
import com.example.social_network.dto.image.ImageResDto;
import com.example.social_network.dto.utils.comment.CommentResponseUtils;
import com.example.social_network.entity.Comment;
import com.example.social_network.entity.Image;
import com.example.social_network.entity.Post;
import com.example.social_network.entity.UserInfo;
import com.example.social_network.exception.AppException;
import com.example.social_network.exception.ErrorCode;
import com.example.social_network.repository.CommentRepository;
import com.example.social_network.repository.PostRepository;
import com.example.social_network.repository.UserInfoRepository;
import com.example.social_network.service.CommentService;
import com.example.social_network.service.FileService;
import com.example.social_network.service.ImageService;
import com.example.social_network.utils.CommonConstants;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private UserInfoRepository userInfoRepository;

	@Autowired
	private FileService fileService;

	@Autowired
	ImageService imageService;

	@Autowired
	CommentResponseUtils commentResponseUtils;

	/**
	 * create comment
	 */
	@Override
	public CommentResDto createComment(CommentReqPostDto reqDto, MultipartFile[] files) {
		Post post = postRepository.findOneById(reqDto.getId());
		if (post == null) {
			throw new AppException(ErrorCode.POST_NOTEXISTED);
		}
		UserInfo userInfor = userInfoRepository
				.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
				.orElseThrow(() -> new UsernameNotFoundException(CommonConstants.USER_NOT_FOUND));

		Comment comment = new Comment();
		comment.setContent(reqDto.getContent());
		comment.setUser(userInfor);
		comment.setPost(post);
		if (files != null && files.length > 0) {
			List<ImageResDto> imageResDto = fileService.uploadImage(files);
			List<Image> imageList = new ArrayList<>();
			if (!CollectionUtils.isEmpty(imageResDto)) {
				imageResDto.stream().forEach(imageDto -> {
					Image imageE = imageService.findOneById(imageDto.getId());
					imageE.setComment(comment);
					imageService.save(imageE);
					imageList.add(imageE);

				});
			}

			comment.getImages().addAll(imageList);
		}
		return commentResponseUtils.convert(commentRepository.save(comment));
	}

	@Override
	public CommentResDto updateComment(CommentReqPutDto reqDto, MultipartFile[] files) {

		Comment comment = commentRepository.findOneById(reqDto.getId());
		UserInfo userInfor = userInfoRepository
				.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
				.orElseThrow(() -> new UsernameNotFoundException(CommonConstants.USER_NOT_FOUND));
		if (comment == null || commentRepository.findByUserAndId(userInfor, reqDto.getId()) == null) {
			throw new AppException(ErrorCode.COMMENT_NOTEXISTED);
		}
		if (files != null && files.length > 0) {
			List<ImageResDto> imageResDto = fileService.uploadImage(files);
			List<Image> imageList = new ArrayList<>();
			if (!CollectionUtils.isEmpty(imageResDto)) {
				imageResDto.stream().forEach(imageDto -> {
					Image imageE = imageService.findOneById(imageDto.getId());
					imageE.setComment(comment);
					imageService.save(imageE);
					imageList.add(imageE);

				});
			}

			comment.getImages().addAll(imageList);
		}
		comment.setContent(reqDto.getContent());

		if (StringUtils.isBlank(comment.getContent()) && reqDto.getUploadFile() == null) {
			throw new AppException(ErrorCode.COMMENT_UPLOAD_WRONG);
		}

		return commentResponseUtils.convert(commentRepository.save(comment));
	}

	@Override
	public void delete(Long id) {
		Comment comment = commentRepository.findOneById(id);
		if (comment == null) {
			throw new AppException(ErrorCode.COMMENT_NOTEXISTED);
		}
		commentRepository.delete(comment);
	}

	@Override
	public Page<CommentResDto> getAllComment(Long id, CommentListReqDto reqDto) {
		Pageable paging = PageRequest.of(reqDto.getPageNo(), reqDto.getPageSize());
		Post post = postRepository.findOneById(id);
		if (post == null) {
			throw new AppException(ErrorCode.POST_NOTEXISTED);
		}
		Page<Comment> pagedResult = commentRepository.findByPost(post, paging);
		List<CommentResDto> commentResponseList = pagedResult.stream().map(commentResponseUtils::convert)
				.collect(Collectors.toList());
		return new PageImpl<>(commentResponseList, paging, commentResponseList.size());
	}
}
