package com.example.social_network.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.social_network.entity.Like;
import com.example.social_network.entity.Post;
import com.example.social_network.entity.UserInfo;
import com.example.social_network.enumdef.PostType;
import com.example.social_network.exception.AppException;
import com.example.social_network.exception.ErrorCode;
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
	UserInfoRepository userInfoRepository;

	@Override
	public void like(Long postId) {

		Post post = postRepository.findOneById(postId);

		if (post == null) {
			throw new AppException(ErrorCode.POST_NOTEXISTED);
		}

		UserInfo userInfor = userInfoRepository
				.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
				.orElseThrow(() -> new UsernameNotFoundException(CommonConstants.USER_NOT_FOUND));
		// if post not exist or post that only me of other user then can not like
		if (postId == null || (post.getPrivacy() == PostType.ONLY_ME.getCode() && userInfor.getId() != post.getUserInfo().getId())) {
			throw new AppException(ErrorCode.POST_NOTEXISTED);
		}

		// check if have liked post before
		Like like = likeRepository.findOneByPostAndUserInfo(post, userInfor);
		if (like == null) {
			Like newLike = Like.builder().userInfo(userInfor).post(post).status(true).build();
			likeRepository.save(newLike);
		} else {
			// if like then dislike, if dislike then like
			like.setStatus(!like.isStatus());
			likeRepository.save(like);
		}
	}
}
