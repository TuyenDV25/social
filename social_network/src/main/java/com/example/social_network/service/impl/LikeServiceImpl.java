package com.example.social_network.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.social_network.entity.Comment;
import com.example.social_network.entity.Like;
import com.example.social_network.entity.Post;
import com.example.social_network.entity.UserInfo;
import com.example.social_network.enumdef.PostType;
import com.example.social_network.exception.AppException;
import com.example.social_network.exception.ErrorCode;
import com.example.social_network.repository.CommentRepository;
import com.example.social_network.repository.LikeRepository;
import com.example.social_network.repository.PostRepository;
import com.example.social_network.repository.UserInfoRepository;
import com.example.social_network.service.LikeService;
import com.example.social_network.utils.CommonConstants;

@Service
public class LikeServiceImpl implements LikeService {

	@Autowired
	private LikeRepository likeRepository;

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	UserInfoRepository userInfoRepository;

	@Override
	public void likePost(Long postId) {

		Post post = postRepository.findOneById(postId);

		if (post == null) {
			throw new AppException(ErrorCode.POST_NOTEXISTED);
		}

		UserInfo userInfor = userInfoRepository
				.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
				.orElseThrow(() -> new UsernameNotFoundException(CommonConstants.USER_NOT_FOUND));
		// if post not exist or post that only me of other user then can not like
		if (post.getPrivacy() == PostType.ONLY_ME.getCode() && userInfor.getId() != post.getUserInfo().getId()) {
			throw new AppException(ErrorCode.POST_NOTEXISTED);
		}

		// check if have liked post before
		Like like = likeRepository.findOneByPostAndUserInfo(post, userInfor);
		if (like == null) {
			Like newLike = new Like();
			newLike.setUserInfo(userInfor);
			newLike.setPost(post);
			likeRepository.save(newLike);
		} else {
			// if like then dislike, if dislike then like
			likeRepository.delete(like);
		}
	}

	@Override
	public void likeComment(Long commentId) {
		Comment comment = commentRepository.findOneById(commentId);
		if (comment == null) {
			throw new AppException(ErrorCode.COMMENT_NOTEXISTED);
		}

		UserInfo userInfor = userInfoRepository
				.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
				.orElseThrow(() -> new UsernameNotFoundException(CommonConstants.USER_NOT_FOUND));

		Post post = postRepository.findByComments(comment);

		if (post == null) {
			throw new AppException(ErrorCode.POST_NOTEXISTED);
		}

		if ((post.getPrivacy() == PostType.ONLY_ME.getCode() && userInfor.getId() != post.getUserInfo().getId())) {
			throw new AppException(ErrorCode.POST_NOTEXISTED);
		}

		// check if have liked post before
		Like like = likeRepository.findOneByCommentAndUserInfo(comment, userInfor);
		if (like == null) {
			Like newLike = new Like();
			newLike.setUserInfo(userInfor);
			newLike.setComment(comment);
			likeRepository.save(newLike);
		} else {
			// if like then dislike, if dislike then like
			likeRepository.delete(like);
		}
	}
}
