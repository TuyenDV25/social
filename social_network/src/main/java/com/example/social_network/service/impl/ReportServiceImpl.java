package com.example.social_network.service.impl;

import java.io.ByteArrayInputStream;
import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
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

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	private UserInfoRepository userInfoRepository;

	@Autowired
	private FriendRepository friendRepository;

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private LikeRepository likeRepository;

	@Override
	public ByteArrayInputStream load() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		UserInfo user = userInfoRepository.findByUsername(authentication.getName())
				.orElseThrow(() -> new UsernameNotFoundException(CommonConstants.USER_NOT_FOUND));

		OffsetDateTime to = OffsetDateTime.now();
		OffsetDateTime from = to.minusDays(7);

		Long countFriend = friendRepository.countNewFriends(user.getId(), from, to);

		Long countPost = postRepository.countPosts(user.getId(), from, to);

		Long countComment = commentRepository.countComment(user.getId(), from, to);

		Long countLike = likeRepository.countLike(user.getId(), from, to);

		return ExcelHelper.tutorialsToExcel(countFriend, countPost, countComment, countLike);

	}
}
