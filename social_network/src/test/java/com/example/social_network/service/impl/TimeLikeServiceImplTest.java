package com.example.social_network.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.social_network.dto.utils.post.PostResponseUtils;
import com.example.social_network.entity.Post;
import com.example.social_network.entity.UserInfo;
import com.example.social_network.repository.PostRepository;
import com.example.social_network.repository.UserInfoRepository;

@ExtendWith(MockitoExtension.class)
public class TimeLikeServiceImplTest {

	@Mock
	private PostRepository postRepository;

	@Mock
	private UserInfoRepository userInfoRepository;

	@Mock
	private PostResponseUtils postResponseUtils;

	private TimeLikeServiceImpl timeLikeServiceImpl;

	@BeforeEach
	public void setUp() {
		timeLikeServiceImpl = new TimeLikeServiceImpl(postRepository, userInfoRepository, postResponseUtils);
	}

	@Test
	void getTimeLinePost_validRequest_success() {

		var authentication = mock(Authentication.class);
		var securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		
		var pageable = PageRequest.of(0, 10);
		List<Post> listUser = new ArrayList<>();

		Page<Post> userInfo = new PageImpl<>(listUser);
		UserInfo user = new UserInfo();
		user.setId(1L);
		when(userInfoRepository.findByUsername(any())).thenReturn(Optional.of(user));
		
		when(postRepository.findByUserInfo(user, pageable)).thenReturn(userInfo);
		
		timeLikeServiceImpl.getTimeLinePost(pageable);
	}

}
