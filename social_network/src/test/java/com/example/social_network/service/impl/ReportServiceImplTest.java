package com.example.social_network.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Optional;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.social_network.entity.UserInfo;
import com.example.social_network.repository.CommentRepository;
import com.example.social_network.repository.FriendRepository;
import com.example.social_network.repository.LikeRepository;
import com.example.social_network.repository.PostRepository;
import com.example.social_network.repository.UserInfoRepository;

@ExtendWith(MockitoExtension.class)
public class ReportServiceImplTest {
	
	@Mock
	private UserInfoRepository userInfoRepository;

	@Mock
	private FriendRepository friendRepository;

	@Mock
	private PostRepository postRepository;

	@Mock
	private CommentRepository commentRepository;

	@Mock
	private LikeRepository likeRepository;
	
	private ReportServiceImpl reportServiceImpl;
	
	@BeforeEach
	public void setUp() {
		reportServiceImpl = new ReportServiceImpl(userInfoRepository, friendRepository, postRepository, commentRepository, likeRepository);
	}
	
	@Test
	void load_validRequest_success() {
		var authentication = mock(Authentication.class);
		var securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		
		Date to = new Date();
		Date from = DateUtils.addDays(to, -7);
		
		UserInfo user = new UserInfo();
		user.setId(1L);
		when(userInfoRepository.findByUsername(any())).thenReturn(Optional.of(user));
		
		when(friendRepository.countNewFriends(any(), any(), any())).thenReturn(1L);
		when(postRepository.countPosts(any(), any(), any())).thenReturn(1L);
		when(commentRepository.countComment(any(), any(), any())).thenReturn(1L);
		when(likeRepository.countLike(any(), any(), any())).thenReturn(1L);
		
		reportServiceImpl.load();
	}


}
