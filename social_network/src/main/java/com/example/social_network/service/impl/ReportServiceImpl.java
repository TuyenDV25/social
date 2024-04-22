package com.example.social_network.service.impl;

import java.io.ByteArrayInputStream;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.social_network.entity.UserInfo;
import com.example.social_network.helper.ExcelHelper;
import com.example.social_network.repository.CommentRepository;
import com.example.social_network.repository.FriendRepository;
import com.example.social_network.repository.LikeRepository;
import com.example.social_network.repository.PostRepository;
import com.example.social_network.repository.UserInfoRepository;
import com.example.social_network.service.ReportService;
import com.example.social_network.utils.CommonConstants;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

	private final UserInfoRepository userInfoRepository;

	private final FriendRepository friendRepository;

	private final PostRepository postRepository;

	private final CommentRepository commentRepository;

	private final LikeRepository likeRepository;

	@Override
	public ByteArrayInputStream load() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		UserInfo user = userInfoRepository.findByUsername(authentication.getName())
				.orElseThrow(() -> new UsernameNotFoundException(CommonConstants.USER_NOT_FOUND));

		Date to = new Date();
		Date from = DateUtils.addDays(to, -7);

		Long countFriend = friendRepository.countNewFriends(user.getId(), from, to);

		long countPost = postRepository.countPosts(user.getId(), from, to);

		Long countComment = commentRepository.countComment(user.getId(), from, to);

		Long countLike = likeRepository.countLike(user.getId(), from, to);

		return ExcelHelper.reportToExcel(countFriend, countPost, countComment, countLike);

	}
}
