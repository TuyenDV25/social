package com.example.social_network.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.social_network.dto.comment.LikeCommentReqDto;
import com.example.social_network.dto.post.LikePostReqDto;
import com.example.social_network.entity.Comment;
import com.example.social_network.entity.Like;
import com.example.social_network.entity.Post;
import com.example.social_network.entity.UserInfo;
import com.example.social_network.exception.AppException;
import com.example.social_network.repository.CommentRepository;
import com.example.social_network.repository.LikeRepository;
import com.example.social_network.repository.PostRepository;
import com.example.social_network.repository.UserInfoRepository;
import com.example.social_network.service.PostService;
import com.example.social_network.utils.Utils;

@ExtendWith(MockitoExtension.class)
public class LikeServiceImplTest {

	@Mock
	private LikeRepository likeRepository;

	@Mock
	private PostRepository postRepository;

	@Mock
	private CommentRepository commentRepository;

	@Mock
	private UserInfoRepository userInfoRepository;

	@Mock
	private PostService postService;

	private LikeServiceImpl likeServiceImpl;

	@BeforeEach
	public void setUp() {
		likeServiceImpl = new LikeServiceImpl(likeRepository, postRepository, commentRepository, userInfoRepository,
				postService);
	}
	
	@Test
	void unlikePost_validRequest_success() {
		var authentication = mock(Authentication.class);
		var securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		
		Post post1 = new Post();
		post1.setContent("post");
		post1.setId(1L);
		post1.setCreatedDate(Utils.StringToDate("2023-12-06 17:03:00"));
		post1.setUpdateDate(Utils.StringToDate("2023-12-06 17:03:00"));
		post1.setPrivacy(3);
		
		when(postRepository.findOneById(1L)).thenReturn(post1);
		
		UserInfo userTest = new UserInfo();
		userTest.setLastName("Tester");
		userTest.setId(1L);
		
		when(userInfoRepository.findByUsername(any())).thenReturn(Optional.of(userTest));
		when(postService.checkRightAccessPost(post1, userTest)).thenReturn(true);
		
		Like like = new Like();
		like.setPost(post1);
		like.setId(1L);
		like.setUserInfo(userTest);
		when(likeRepository.findOneByPostAndUserInfo(post1, userTest)).thenReturn(like);
		LikePostReqDto reqDto = new LikePostReqDto();
		reqDto.setId(1L);
		likeServiceImpl.likePost(reqDto);
	}
	
	@Test
	void likePost_validRequest_success() {
		var authentication = mock(Authentication.class);
		var securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		
		Post post1 = new Post();
		post1.setContent("post");
		post1.setId(1L);
		post1.setCreatedDate(Utils.StringToDate("2023-12-06 17:03:00"));
		post1.setUpdateDate(Utils.StringToDate("2023-12-06 17:03:00"));
		post1.setPrivacy(3);
		
		when(postRepository.findOneById(1L)).thenReturn(post1);
		
		UserInfo userTest = new UserInfo();
		userTest.setLastName("Tester");
		userTest.setId(1L);
		
		when(userInfoRepository.findByUsername(any())).thenReturn(Optional.of(userTest));
		when(postService.checkRightAccessPost(post1, userTest)).thenReturn(true);
		when(likeRepository.findOneByPostAndUserInfo(post1, userTest)).thenReturn(null);
		
		LikePostReqDto reqDto = new LikePostReqDto();
		reqDto.setId(1L);
		likeServiceImpl.likePost(reqDto);
	}
	
	@Test
	void likePost_validRequest_notExistPost_fail() {
		when(postRepository.findOneById(1L)).thenReturn(null);
		
		LikePostReqDto reqDto = new LikePostReqDto();
		reqDto.setId(1L);
		
		AppException exception = assertThrows(AppException.class, () -> likeServiceImpl.likePost(reqDto));

		assertEquals(1002, exception.getErrorCode().getCode());
		assertEquals("post not existed", exception.getMessage());
		
	}
	
	@Test
	void likePost_validRequest_haveNoRight_fail() {
		var authentication = mock(Authentication.class);
		var securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		
		Post post1 = new Post();
		post1.setContent("post");
		post1.setId(1L);
		
		UserInfo userTest = new UserInfo();
		userTest.setLastName("Tester");
		userTest.setId(1L);

		when(postRepository.findOneById(1L)).thenReturn(post1);
		
		when(userInfoRepository.findByUsername(any())).thenReturn(Optional.of(userTest));
		when(postService.checkRightAccessPost(post1, userTest)).thenReturn(false);
		
		LikePostReqDto reqDto = new LikePostReqDto();
		reqDto.setId(1L);
		
		AppException exception = assertThrows(AppException.class, () -> likeServiceImpl.likePost(reqDto));

		assertEquals(1002, exception.getErrorCode().getCode());
		assertEquals("post not existed", exception.getMessage());
	}
	
	@Test
	void likeComment_validRequest_success() {
		var authentication = mock(Authentication.class);
		var securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		
		Post post1 = new Post();
		post1.setContent("post");
		post1.setId(1L);
		post1.setCreatedDate(Utils.StringToDate("2023-12-06 17:03:00"));
		post1.setUpdateDate(Utils.StringToDate("2023-12-06 17:03:00"));
		post1.setPrivacy(1);
		
		Comment comment1 = new Comment();
		comment1.setContent("comment");
		comment1.setId(1L);
		comment1.setPost(post1);
		
		when(commentRepository.findOneById(1L)).thenReturn(comment1);
		
		when(postRepository.findByComments(comment1)).thenReturn(post1);
		
		UserInfo userTest = new UserInfo();
		userTest.setLastName("Tester");
		userTest.setId(1L);
		
		when(userInfoRepository.findByUsername(any())).thenReturn(Optional.of(userTest));
		when(postService.checkRightAccessPost(post1, userTest)).thenReturn(true);
		
		Like like = new Like();
		like.setPost(post1);
		like.setId(1L);
		like.setUserInfo(userTest);
		when(likeRepository.findOneByCommentAndUserInfo(comment1, userTest)).thenReturn(like);
		LikeCommentReqDto reqDto = new LikeCommentReqDto();
		reqDto.setId(1L);
		likeServiceImpl.likeComment(reqDto);
	}
	
	@Test
	void unlikeComment_validRequest_success() {
		var authentication = mock(Authentication.class);
		var securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		
		Post post1 = new Post();
		post1.setContent("post");
		post1.setId(1L);
		post1.setCreatedDate(Utils.StringToDate("2023-12-06 17:03:00"));
		post1.setUpdateDate(Utils.StringToDate("2023-12-06 17:03:00"));
		post1.setPrivacy(1);
		
		Comment comment1 = new Comment();
		comment1.setContent("comment");
		comment1.setId(1L);
		comment1.setPost(post1);
		
		when(commentRepository.findOneById(1L)).thenReturn(comment1);
		
		when(postRepository.findByComments(comment1)).thenReturn(post1);
		
		UserInfo userTest = new UserInfo();
		userTest.setLastName("Tester");
		userTest.setId(1L);
		
		when(userInfoRepository.findByUsername(any())).thenReturn(Optional.of(userTest));
		when(postService.checkRightAccessPost(post1, userTest)).thenReturn(true);
		
		when(likeRepository.findOneByCommentAndUserInfo(comment1, userTest)).thenReturn(null);
		LikeCommentReqDto reqDto = new LikeCommentReqDto();
		reqDto.setId(1L);
		likeServiceImpl.likeComment(reqDto);
	}
	
	@Test
	void likeComment_validRequest_NotHaveComment_fail() {
		LikeCommentReqDto reqDto = new LikeCommentReqDto();
		reqDto.setId(1L);
		when(commentRepository.findOneById(1L)).thenReturn(null);
		AppException exception = assertThrows(AppException.class, () -> likeServiceImpl.likeComment(reqDto));

		assertEquals(1002, exception.getErrorCode().getCode());
		assertEquals("comment not existed", exception.getMessage());

	}
	
	@Test
	void likeComment_validRequest_notHaveRight_success() {
		var authentication = mock(Authentication.class);
		var securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		
		UserInfo userTest1 = new UserInfo();
		userTest1.setLastName("Tester");
		userTest1.setId(1L);
		
		Comment comment1 = new Comment();
		comment1.setContent("comment");
		comment1.setId(1L);
		
		Post post1 = new Post();
		post1.setContent("post");
		post1.setId(1L);
		post1.setUserInfo(userTest1);
		post1.setPrivacy(3);

		when(commentRepository.findOneById(1L)).thenReturn(comment1);
		
		when(postRepository.findByComments(comment1)).thenReturn(post1);
		
		UserInfo userTest = new UserInfo();
		userTest.setLastName("Tester");
		userTest.setId(1L);
		
		when(userInfoRepository.findByUsername(any())).thenReturn(Optional.of(userTest));
		when(postService.checkRightAccessPost(post1, userTest)).thenReturn(false);
		
		LikeCommentReqDto reqDto = new LikeCommentReqDto();
		reqDto.setId(1L);
		AppException exception = assertThrows(AppException.class, () -> likeServiceImpl.likeComment(reqDto));

		assertEquals(1002, exception.getErrorCode().getCode());
		assertEquals("post not existed", exception.getMessage());
	}

}
