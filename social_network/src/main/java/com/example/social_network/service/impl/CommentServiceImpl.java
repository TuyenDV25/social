package com.example.social_network.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
import com.example.social_network.service.PostService;
import com.example.social_network.utils.CommonConstants;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

	private final PostRepository postRepository;

	private final CommentRepository commentRepository;

	private final UserInfoRepository userInfoRepository;

	private final FileService fileService;

	private final ImageService imageService;

	private final PostService postService;

	private final CommentResponseUtils commentResponseUtils;

	/**
	 * create comment
	 */
	@Override
	public CommentResDto createComment(Long postId, CommentReqPostDto reqDto, MultipartFile files) {

		if (StringUtils.isBlank(reqDto.getContent()) && files == null) {
			throw new AppException(ErrorCode.COMMENT_UPLOAD_WRONG);
		}

		Post post = postRepository.findOneById(postId);

		UserInfo userInfor = userInfoRepository
				.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
				.orElseThrow(() -> new UsernameNotFoundException(CommonConstants.USER_NOT_FOUND));

		if (post == null || !postService.checkRightAccessPost(post, userInfor)) {
			throw new AppException(ErrorCode.POST_NOTEXISTED);
		}

		Comment comment = new Comment();
		comment.setContent(reqDto.getContent());
		comment.setUser(userInfor);
		comment.setPost(post);

		Comment fixedComment = commentRepository.save(comment);
		if (files != null) {
			MultipartFile[] file = { files };
			List<ImageResDto> imageResDto = fileService.uploadImage(file);
			if (imageResDto.size() > 0) {
				Image imageE = imageService.findOneById(imageResDto.get(0).getId());
				imageE.setComment(comment);
				imageService.save(imageE);
				fixedComment.setImage(imageE);
			}
		}
		return commentResponseUtils.convert(fixedComment);
	}

	@Override
	public CommentResDto updateComment(Long commentId, CommentReqPutDto reqDto, MultipartFile files) {

		Comment comment = commentRepository.findOneById(commentId);

		UserInfo userInfor = userInfoRepository
				.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
				.orElseThrow(() -> new UsernameNotFoundException(CommonConstants.USER_NOT_FOUND));

		if (comment == null || !postService.checkRightAccessPost(comment.getPost(), userInfor)) {
			throw new AppException(ErrorCode.COMMENT_NOTEXISTED);
		}
		
		if (commentRepository.findByUserAndId(userInfor, commentId) == null) {
			throw new AppException(ErrorCode.COMMENT_NOT_RIGHT);
		}

		if (!checkDataUpdateOK(comment, reqDto, files)) {
			throw new AppException(ErrorCode.COMMENT_UPLOAD_WRONG);
		}

		comment.setContent(reqDto.getContent());
		Comment updateComment = commentRepository.save(comment);

		// delete
		if (reqDto.getDeleteImageId() != null) {
			imageService.deleteById(reqDto.getDeleteImageId());
			updateComment.setImage(null);
		}

		if (files != null) {
			MultipartFile[] file = { files };
			List<ImageResDto> imageResDto = fileService.uploadImage(file);
			if (imageResDto.size() > 0) {

				Image imageE = imageService.findOneById(imageResDto.get(0).getId());
				imageE.setComment(comment);
				imageService.save(imageE);
				updateComment.setImage(imageE);
			}
		}
		return commentResponseUtils.convert(updateComment);
	}

	@Override
	public void delete(Long id) {
		Comment comment = commentRepository.findOneById(id);

		UserInfo userInfor = userInfoRepository
				.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
				.orElseThrow(() -> new UsernameNotFoundException(CommonConstants.USER_NOT_FOUND));

		if (comment == null || !postService.checkRightAccessPost(comment.getPost(), userInfor)) {
			throw new AppException(ErrorCode.COMMENT_NOTEXISTED);
		}
		
		if (commentRepository.findByUserAndId(userInfor, comment.getId()) == null) {
			throw new AppException(ErrorCode.COMMENT_NOT_RIGHT);
		}
		commentRepository.delete(comment);
	}

	@Override
	public Page<CommentResDto> getAllComment(Long id, Integer pageNo) {
		Pageable paging = PageRequest.of(pageNo, 10);
		Post post = postRepository.findOneById(id);

		UserInfo userInfor = userInfoRepository
				.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
				.orElseThrow(() -> new UsernameNotFoundException(CommonConstants.USER_NOT_FOUND));

		if (post == null || !postService.checkRightAccessPost(post, userInfor)) {
			throw new AppException(ErrorCode.POST_NOTEXISTED);
		}
		Page<Comment> pagedResult = commentRepository.findByPost(post, paging);
		List<CommentResDto> commentResponseList = pagedResult.stream().map(commentResponseUtils::convert)
				.collect(Collectors.toList());
		return new PageImpl<>(commentResponseList, paging, commentResponseList.size());
	}

	/**
	 * check if data input update comment is ok
	 * 
	 * @param comment {@link Comment}
	 * @param reqDto  data input
	 * @param files   file upload
	 * @return true if data input ok, false otherwise
	 */
	private boolean checkDataUpdateOK(Comment comment, CommentReqPutDto reqDto, MultipartFile files) {
		if (checkDeleteImage(comment, reqDto)) {
			throw new AppException(ErrorCode.DELETE_IMAGE_COMMENT_WRONG);
		}
		// case only have content, if update no content and no upload image will be
		// error
		if ((comment.getImage() == null)) {
			if (files == null && StringUtils.isBlank(reqDto.getContent())) {
				return false;
			}
		} else {
			if (files == null && reqDto.getDeleteImageId() != null && StringUtils.isBlank(reqDto.getContent())) {
				return false;
			}
		}
		return true;
	}

	private boolean checkDeleteImage(Comment comment, CommentReqPutDto reqDto) {
		if (reqDto.getDeleteImageId() != null && comment.getImage() == null) {
			return true;
		}

		if (reqDto.getDeleteImageId() != null && comment.getImage() != null
				&& reqDto.getDeleteImageId() != comment.getImage().getId()) {
			return true;
		}

		return false;
	}
}
