package com.example.social_network.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import org.springframework.web.multipart.MultipartFile;

import com.example.social_network.dto.comment.CommentReqPostDto;
import com.example.social_network.dto.comment.CommentReqPutDto;
import com.example.social_network.dto.comment.CommentResDto;
import com.example.social_network.dto.image.ImageResDto;
import com.example.social_network.dto.user.UserInforResDto;
import com.example.social_network.dto.utils.comment.CommentResponseUtils;
import com.example.social_network.entity.Comment;
import com.example.social_network.entity.Image;
import com.example.social_network.entity.Post;
import com.example.social_network.entity.UserInfo;
import com.example.social_network.exception.AppException;
import com.example.social_network.repository.CommentRepository;
import com.example.social_network.repository.PostRepository;
import com.example.social_network.repository.UserInfoRepository;
import com.example.social_network.service.FileService;
import com.example.social_network.service.ImageService;
import com.example.social_network.service.PostService;
import com.example.social_network.utils.Utils;

@ExtendWith(MockitoExtension.class)
public class CommentServiceImplTest {

	@Mock
	private PostRepository postRepository;

	@Mock
	private CommentRepository commentRepository;

	@Mock
	private UserInfoRepository userInfoRepository;

	@Mock
	private FileService fileService;

	@Mock
	private ImageService imageService;

	@Mock
	private PostService postService;

	@Mock
	private CommentResponseUtils commentResponseUtils;

	private CommentServiceImpl commentServiceImpl;

	@BeforeEach
	public void setUp() {
		commentServiceImpl = new CommentServiceImpl(postRepository, commentRepository, userInfoRepository, fileService,
				imageService, postService, commentResponseUtils);
	}

	@Test
	void CreateComment_validRequest_success() {
		var authentication = mock(Authentication.class);
		var securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);

		CommentReqPostDto reqDto = new CommentReqPostDto();
		reqDto.setContent("hello");

		Post post = new Post();

		UserInfo userTest = new UserInfo();
		userTest.setLastName("Tester");
		userTest.setFirstName("Dov");
		userTest.setDob(Utils.convertStringToLocalDate("19991111"));
		userTest.setIntroyourself("minh ten a");
		userTest.setGender(true);
		userTest.setAvatarImage(new ArrayList<Image>());

		when(postRepository.findOneById(1L)).thenReturn(post);

		when(userInfoRepository.findByUsername(any())).thenReturn(Optional.of(userTest));

		when(postService.checkRightAccessPost(post, userTest)).thenReturn(true);

		Comment comment = new Comment();
		comment.setContent("hello");
		comment.setUser(userTest);
		comment.setPost(post);

		when(commentRepository.save(any())).thenReturn(comment);

		CommentResDto resDto = new CommentResDto();
		resDto.setContent("hi");
		UserInforResDto userDto = new UserInforResDto();
		userDto.setLastName("Tester");
		userDto.setFirstName("Dov");
		userDto.setDob(Utils.convertStringToLocalDate("19991111"));
		userDto.setIntroyourself("minh ten a");
		userDto.setGender(true);
		userDto.setAvatar(new ImageResDto());
		resDto.setUserInfo(userDto);

		when(commentResponseUtils.convert(comment)).thenReturn(resDto);

		MultipartFile file = mock(MultipartFile.class);
		var result = commentServiceImpl.createComment(1L, reqDto, file);

		assertEquals("hi", result.getContent());
		assertEquals("Dov", result.getUserInfo().getFirstName());
		assertEquals(Utils.convertStringToLocalDate("19991111"), result.getUserInfo().getDob());
	}

	@Test
	void CreateComment_validRequest_InputWrong_fail() {
		CommentReqPostDto reqDto = new CommentReqPostDto();
		reqDto.setContent("");
		AppException exception = assertThrows(AppException.class,
				() -> commentServiceImpl.createComment(1L, reqDto, null));

		assertEquals(1004, exception.getErrorCode().getCode());
		assertEquals("comment must have at least content or file", exception.getMessage());
	}

	@Test
	void CreateComment_validRequest_NotRight_fail() {
		var authentication = mock(Authentication.class);
		var securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);

		CommentReqPostDto reqDto = new CommentReqPostDto();
		reqDto.setContent("hj");
		when(postRepository.findOneById(1L)).thenReturn(null);
		when(userInfoRepository.findByUsername(any())).thenReturn(Optional.of(mock(UserInfo.class)));

		AppException exception = assertThrows(AppException.class,
				() -> commentServiceImpl.createComment(1L, reqDto, null));

		assertEquals(1002, exception.getErrorCode().getCode());
		assertEquals("post not existed", exception.getMessage());
	}

	@Test
	void UpdateComment_validRequest_success() {
		var authentication = mock(Authentication.class);
		var securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);

		Comment comment = new Comment();
		comment.setContent("nice");
		Image image = new Image();
		image.setId(1L);
		image.setLinkImage("abc.com");
		comment.setImage(image);

		UserInfo userTest = new UserInfo();
		userTest.setLastName("Tester");
		userTest.setFirstName("Dov");
		userTest.setDob(Utils.convertStringToLocalDate("19991111"));
		userTest.setIntroyourself("minh ten a");
		userTest.setGender(true);
		userTest.setAvatarImage(new ArrayList<Image>());

		when(commentRepository.findOneById(1L)).thenReturn(comment);
		when(userInfoRepository.findByUsername(any())).thenReturn(Optional.of(userTest));
		when(commentRepository.findByUserAndId(userTest, 1L)).thenReturn(comment);

		when(postService.checkRightAccessPost(comment.getPost(), userTest)).thenReturn(true);

		Comment comment2 = new Comment();
		comment2.setContent("bicisi");
		comment2.setUser(userTest);
		comment2.setImage(image);

		when(commentRepository.save(comment)).thenReturn(comment2);

		CommentReqPutDto reqDto = new CommentReqPutDto();
		reqDto.setContent("bicisi");
		reqDto.setDeleteImageId(1L);

		CommentResDto resDto = new CommentResDto();
		resDto.setContent("bicisi");
		resDto.setImage(new ImageResDto());
		UserInforResDto u = new UserInforResDto();
		u.setFirstName("Dov");
		u.setDob(Utils.convertStringToLocalDate("19991111"));
		resDto.setUserInfo(u);

		when(commentResponseUtils.convert(comment2)).thenReturn(resDto);

		MultipartFile file = mock(MultipartFile.class);
		var result = commentServiceImpl.updateComment(1L, reqDto, file);

		assertEquals("bicisi", result.getContent());
		assertEquals("Dov", result.getUserInfo().getFirstName());
		assertEquals(Utils.convertStringToLocalDate("19991111"), result.getUserInfo().getDob());
	}

	@Test
	void UpdateComment_validRequest_wrongInput_fail() {
		var authentication = mock(Authentication.class);
		var securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		CommentReqPutDto reqDto = new CommentReqPutDto();
		reqDto.setContent("");
		Comment comment = new Comment();
		comment.setContent("");
		UserInfo userTest = new UserInfo();
		userTest.setLastName("Tester");
		userTest.setFirstName("Dov");

		when(commentRepository.findOneById(1L)).thenReturn(comment);
		when(userInfoRepository.findByUsername(any())).thenReturn(Optional.of(userTest));
		when(commentRepository.findByUserAndId(userTest, 1L)).thenReturn(comment);

		when(postService.checkRightAccessPost(comment.getPost(), userTest)).thenReturn(true);

		AppException exception = assertThrows(AppException.class,
				() -> commentServiceImpl.updateComment(1L, reqDto, null));

		assertEquals(1004, exception.getErrorCode().getCode());
		assertEquals("comment must have at least content or file", exception.getMessage());
	}

	@Test
	void UpdateComment_validRequest_NotRight_fail() {
		var authentication = mock(Authentication.class);
		var securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		CommentReqPutDto reqDto = new CommentReqPutDto();
		reqDto.setContent("bb");
		when(commentRepository.findOneById(1L)).thenReturn(new Comment());
		
		UserInfo userTest = new UserInfo();
		userTest.setLastName("Tester");
		when(userInfoRepository.findByUsername(any())).thenReturn(Optional.of(userTest));

		AppException exception = assertThrows(AppException.class,
				() -> commentServiceImpl.updateComment(1L, reqDto, null));

		assertEquals(1002, exception.getErrorCode().getCode());
		assertEquals("comment not existed", exception.getMessage());
	}
	
	@Test
	void DeleteComment_validRequest_success() {
		var authentication = mock(Authentication.class);
		var securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		Comment comment = new Comment();
		comment.setContent("tes");
		comment.setId(1L);
		
		Post post = new Post();
		post.setPrivacy(1);
		post.setId(1L);
		comment.setPost(post);
		
		when(commentRepository.findOneById(1L)).thenReturn(comment);
		UserInfo userTest = new UserInfo();
		userTest.setLastName("Tester");
		when(userInfoRepository.findByUsername(any())).thenReturn(Optional.of(userTest));
		when(commentRepository.findByUserAndId(userTest, 1L)).thenReturn(mock( Comment.class));
		when(postService.checkRightAccessPost(comment.getPost(), userTest)).thenReturn(true);
		
		commentServiceImpl.delete(1L);
	}
	
	@Test
	void DeleteComment_validRequest_commentNull_fail() {
		var authentication = mock(Authentication.class);
		var securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		Comment comment = new Comment();
		comment.setContent("tes");
		
		when(commentRepository.findOneById(1L)).thenReturn(comment);
		UserInfo userTest = new UserInfo();
		userTest.setLastName("Tester");
		when(userInfoRepository.findByUsername(any())).thenReturn(Optional.of(userTest));
		
		AppException exception = assertThrows(AppException.class,
				() -> commentServiceImpl.delete(1L));

		assertEquals(1002, exception.getErrorCode().getCode());
		assertEquals("comment not existed", exception.getMessage());
	}
	
	@Test
	void GetAllComment_validRequest_success() {
		var authentication = mock(Authentication.class);
		var securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		Post comment = new Post();
		comment.setContent("tes");
		
		when(postRepository.findOneById(1L)).thenReturn(comment);
		UserInfo userTest = new UserInfo();
		userTest.setLastName("Tester");
		when(userInfoRepository.findByUsername(any())).thenReturn(Optional.of(userTest));
		when(postService.checkRightAccessPost(comment, userTest)).thenReturn(true);
		
		List<Comment> listUser = new ArrayList<>();
		Page<Comment> userInfo = new PageImpl<>(listUser);
		var pageable = PageRequest.of(0, 10);
		when(commentRepository.findByPost(comment, pageable)).thenReturn(userInfo);
		
		commentServiceImpl.getAllComment(1L, 0);
	}
	
	@Test
	void GetAllComment_validRequest_fail() {
		var authentication = mock(Authentication.class);
		var securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		Post post = new Post();
		
		when(postRepository.findOneById(1L)).thenReturn(post);
		
		UserInfo userTest = new UserInfo();
		userTest.setLastName("Tester");
		when(userInfoRepository.findByUsername(any())).thenReturn(Optional.of(userTest));
		when(postService.checkRightAccessPost(post, userTest)).thenReturn(false);
		
		AppException exception = assertThrows(AppException.class,
				() -> commentServiceImpl.getAllComment(1L, 0));
		assertEquals(1002, exception.getErrorCode().getCode());
		assertEquals("post not existed", exception.getMessage());
		
	}
}
