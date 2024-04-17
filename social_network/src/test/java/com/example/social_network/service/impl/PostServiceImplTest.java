package com.example.social_network.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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

import com.example.social_network.dto.image.ImageResDto;
import com.example.social_network.dto.post.DeletePostResDto;
import com.example.social_network.dto.post.PostPostReqDto;
import com.example.social_network.dto.post.PostPostResDto;
import com.example.social_network.dto.post.PostPrivacyPutReqDto;
import com.example.social_network.dto.post.PostPutReqDto;
import com.example.social_network.dto.user.UserInforResDto;
import com.example.social_network.dto.utils.post.PostResponseUtils;
import com.example.social_network.entity.Image;
import com.example.social_network.entity.Post;
import com.example.social_network.entity.UserInfo;
import com.example.social_network.exception.AppException;
import com.example.social_network.mapper.post.PostRequestMapper;
import com.example.social_network.repository.PostRepository;
import com.example.social_network.repository.UserInfoRepository;
import com.example.social_network.service.FileService;
import com.example.social_network.service.ImageService;
import com.example.social_network.utils.Utils;

@ExtendWith(MockitoExtension.class)
public class PostServiceImplTest {

	@Mock
	private PostRequestMapper postRequestMapper;

	@Mock
	private FileService fileService;

	@Mock
	private ImageService imageService;

	@Mock
	private UserInfoRepository userInfoRepository;

	@Mock
	private PostRepository postRepository;

	@Mock
	private PostResponseUtils postResponseUtils;

	private PostServiceImpl postServiceImpl;

	@BeforeEach
	public void setUp() {
		postServiceImpl = new PostServiceImpl(postRequestMapper, fileService, imageService, userInfoRepository,
				postRepository, postResponseUtils);
	}

	@Test
	void CreatePost_validRequest_success() {
		var authentication = mock(Authentication.class);
		var securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);

		PostPostReqDto reqDto = new PostPostReqDto();

		Post post = new Post();
		post.setContent("post test");
		post.setPrivacy(1);
		when(postRequestMapper.dtoToEntity(reqDto)).thenReturn(post);
		UserInfo userTest = new UserInfo();
		userTest.setLastName("Tester");
		userTest.setFirstName("Dov");
		userTest.setDob(Utils.convertStringToLocalDate("19991111"));
		userTest.setIntroyourself("minh ten a");
		userTest.setGender(true);
		userTest.setAvatarImage(new ArrayList<Image>());

		when(userInfoRepository.findByUsername(any())).thenReturn(Optional.of(userTest));
		var listImage = new ArrayList<ImageResDto>();
		listImage.add(ImageResDto.builder().id(1).linkImage("abc.com").build());

		when(postRepository.save(any())).thenReturn(post);

		when(fileService.uploadImage(any())).thenReturn(listImage);
		Image image = new Image();
		image.setId(1L);
		image.setLinkImage("abc.com");
		when(imageService.findOneById(1L)).thenReturn(image);

		UserInforResDto userResDto = new UserInforResDto();
		userResDto.setFirstName("Dov");
		userResDto.setLastName("Tester");

		PostPostResDto resDto = new PostPostResDto();
		resDto.setContent("post test");
		resDto.setImage(listImage);
		resDto.setPrivacy(1);
		resDto.setUserInfo(userResDto);

		when(postResponseUtils.convert(post)).thenReturn(resDto);

		MultipartFile[] file = { mock(MultipartFile.class) };

		var result = postServiceImpl.createPost(reqDto, file);
		assertEquals(1, result.getPrivacy());
		assertEquals("post test", result.getContent());
		assertEquals(listImage, result.getImage());
		assertEquals("Tester", result.getUserInfo().getLastName());

		verify(postRepository, times(1)).save(any());
	}

	@Test
	void CreatePost_validRequest_fail() {
		var authentication = mock(Authentication.class);
		var securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		PostPostReqDto reqDto = new PostPostReqDto();

		UserInfo userTest = new UserInfo();
		userTest.setLastName("Tester");
		userTest.setFirstName("Dov");
		userTest.setDob(Utils.convertStringToLocalDate("19991111"));
		userTest.setIntroyourself("minh ten a");
		userTest.setGender(true);
		userTest.setAvatarImage(new ArrayList<Image>());

		when(userInfoRepository.findByUsername(any())).thenReturn(Optional.of(userTest));
		AppException exception = assertThrows(AppException.class, () -> postServiceImpl.createPost(reqDto, null));

		assertEquals(1004, exception.getErrorCode().getCode());
		assertEquals("post must have at least content or file", exception.getMessage());
	}

	@Test
	void updatePost_validRequest_success() {
		var authentication = mock(Authentication.class);
		var securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);

		UserInfo userTest = new UserInfo();
		userTest.setLastName("Tester");
		userTest.setFirstName("Dov");
		userTest.setDob(Utils.convertStringToLocalDate("19991111"));
		userTest.setIntroyourself("minh ten a");
		userTest.setGender(true);
		userTest.setAvatarImage(new ArrayList<Image>());

		when(userInfoRepository.findByUsername(any())).thenReturn(Optional.of(userTest));

		PostPutReqDto reqDto = new PostPutReqDto();
		reqDto.setContent("nay di chơi");
		List<Long> list = new ArrayList<Long>();
		list.add(1L);
		reqDto.setListImageIdDeletes(list);

		Post post = new Post();
		post.setContent("post test");
		post.setPrivacy(3);
		List<Image> imageList = new ArrayList<Image>();
		Image image3 = new Image();
		image3.setId(1L);
		image3.setLinkImage("24h.com");
		imageList.add(image3);
		post.setImages(imageList);
		when(postRepository.findOneById(1L)).thenReturn(post);

		when(postRepository.findByUserInfoAndId(any(), any())).thenReturn(post);

		Image image = new Image();
		image.setId(1L);
		image.setLinkImage("24h.com");

//		Image image2 = new Image();
//		image.setId(2L);
//		image.setLinkImage("abc.com");

		when(imageService.findOneById(1L)).thenReturn(image);

		Post afterSavePost = new Post();
		afterSavePost.setContent("nay di chơi");
		afterSavePost.setPrivacy(1);

		when(postRepository.save(post)).thenReturn(afterSavePost);

//		var listImage = new ArrayList<ImageResDto>();
//		listImage.add(ImageResDto.builder().id(2).linkImage("abc.com").build());
//		when(fileService.uploadImage(any())).thenReturn(listImage);
//		
//		when(imageService.findOneById(2L)).thenReturn(image2);

		PostPostResDto resDto = new PostPostResDto();
		resDto.setContent("nay di chơi");
		resDto.setPrivacy(1);

		var listImage2 = new ArrayList<ImageResDto>();
		listImage2.add(ImageResDto.builder().id(2).linkImage("abc.com").build());
		resDto.setImage(listImage2);
		when(postResponseUtils.convert(afterSavePost)).thenReturn(resDto);

		var result = postServiceImpl.update(1L, reqDto, null);

		assertEquals("nay di chơi", result.getContent());
		assertEquals(1, result.getPrivacy());
		assertEquals("abc.com", result.getImage().get(0).getLinkImage());

		verify(imageService, times(1)).deleteById(any());

	}

	@Test
	void UploadPost_validRequest_NotExistPost_fail() {
		var authentication = mock(Authentication.class);
		var securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		UserInfo userTest = new UserInfo();
		userTest.setLastName("Tester");
		userTest.setFirstName("Dov");
		userTest.setDob(Utils.convertStringToLocalDate("19991111"));
		userTest.setIntroyourself("minh ten a");
		userTest.setGender(true);
		userTest.setAvatarImage(new ArrayList<Image>());

		when(userInfoRepository.findByUsername(any())).thenReturn(Optional.of(userTest));

		PostPutReqDto reqDto = new PostPutReqDto();
		when(postRepository.findOneById(any())).thenReturn(null);
		AppException exception = assertThrows(AppException.class, () -> postServiceImpl.update(1L, reqDto, null));

		assertEquals(1002, exception.getErrorCode().getCode());
		assertEquals("post not existed", exception.getMessage());
	}

	@Test
	void UploadPost_validRequest_NotHaveRight_fail() {
		var authentication = mock(Authentication.class);
		var securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		UserInfo userTest = new UserInfo();
		userTest.setLastName("Tester");
		userTest.setFirstName("Dov");
		userTest.setDob(Utils.convertStringToLocalDate("19991111"));
		userTest.setIntroyourself("minh ten a");
		userTest.setGender(true);
		userTest.setAvatarImage(new ArrayList<Image>());

		when(userInfoRepository.findByUsername(any())).thenReturn(Optional.of(userTest));

		PostPutReqDto reqDto = new PostPutReqDto();
		when(postRepository.findOneById(any())).thenReturn(mock(Post.class));
		when(postRepository.findByUserInfoAndId(any(), any())).thenReturn(null);
		AppException exception = assertThrows(AppException.class, () -> postServiceImpl.update(1L, reqDto, null));

		assertEquals(1002, exception.getErrorCode().getCode());
		assertEquals("you can not fix or delete other's post", exception.getMessage());
	}

	@Test
	void UploadPost_validRequest_InputNotOk_fail() {
		var authentication = mock(Authentication.class);
		var securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		UserInfo userTest = new UserInfo();
		userTest.setLastName("Tester");
		userTest.setFirstName("Dov");
		userTest.setDob(Utils.convertStringToLocalDate("19991111"));
		userTest.setIntroyourself("minh ten a");
		userTest.setGender(true);
		userTest.setAvatarImage(new ArrayList<Image>());

		when(userInfoRepository.findByUsername(any())).thenReturn(Optional.of(userTest));

		PostPutReqDto reqDto = new PostPutReqDto();
		Post post = new Post();
		when(postRepository.findOneById(any())).thenReturn(post);
		when(postRepository.findByUserInfoAndId(any(), any())).thenReturn(mock(Post.class));
		AppException exception = assertThrows(AppException.class, () -> postServiceImpl.update(1L, reqDto, null));

		assertEquals(1004, exception.getErrorCode().getCode());
		assertEquals("post must have at least content or file", exception.getMessage());
	}

	@Test
	void deletePost_validRequest_success() {
		var authentication = mock(Authentication.class);
		var securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		UserInfo user = new UserInfo();
		user.setId(1L);
		Post post = new Post();
		post.setId(1L);
		post.setContent("testPost");
		post.setUserInfo(user);
		post.setPrivacy(1);
		when(postRepository.findOneById(any())).thenReturn(post);

		when(userInfoRepository.findByUsername(any())).thenReturn(Optional.of(user));

		when(postRepository.findByUserInfoAndId(any(), any())).thenReturn(post);

		DeletePostResDto resDto = new DeletePostResDto();
		resDto.setId(1L);
		var result = postServiceImpl.delete(1L);

		assertEquals(1L, result.getId());

		verify(postRepository, times(1)).deleteById(any());

	}

	@Test
	void deletePost_validRequest_NotExistPost_fail() {
		when(postRepository.findOneById(any())).thenReturn(null);
		AppException exception = assertThrows(AppException.class, () -> postServiceImpl.delete(1L));

		assertEquals(1002, exception.getErrorCode().getCode());
		assertEquals("post not existed", exception.getMessage());
	}

	@Test
	void deletePost_validRequest_NotRight_fail() {
		var authentication = mock(Authentication.class);
		var securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		UserInfo user2 = new UserInfo();
		user2.setId(2L);

		Post post = new Post();
		post.setPrivacy(3);
		post.setId(1L);
		post.setUserInfo(user2);

		UserInfo user = new UserInfo();
		user.setId(1L);

		when(postRepository.findOneById(any())).thenReturn(post);
		when(userInfoRepository.findByUsername(any())).thenReturn(Optional.of(user));

		AppException exception = assertThrows(AppException.class, () -> postServiceImpl.delete(1L));

		assertEquals(1002, exception.getErrorCode().getCode());
		assertEquals("you can not fix or delete other's post", exception.getMessage());
	}

	@Test
	void DetailPost_validRequest_success() {
		var authentication = mock(Authentication.class);
		var securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		UserInfo user = new UserInfo();
		user.setId(1L);
		Post post = new Post();
		post.setId(1L);
		post.setContent("testPost");
		post.setUserInfo(user);
		post.setPrivacy(1);
		when(postRepository.findOneById(any())).thenReturn(post);

		when(userInfoRepository.findByUsername(any())).thenReturn(Optional.of(user));

		PostPostResDto resDto = new PostPostResDto();
		resDto.setContent("testPost");
		resDto.setId(1L);
		resDto.setPrivacy(1);
		UserInforResDto userDto = new UserInforResDto();
		userDto.setId(1L);
		resDto.setUserInfo(userDto);

		when(postResponseUtils.convert(post)).thenReturn(resDto);

		var result = postServiceImpl.getPostDetail(1L);

		assertEquals(1L, result.getId());
		assertEquals("testPost", result.getContent());
		assertEquals(1, result.getPrivacy());
		assertEquals(1L, result.getUserInfo().getId());

	}

	@Test
	void GetPost_validRequest_NotExistPost_fail() {
		when(postRepository.findOneById(any())).thenReturn(null);
		AppException exception = assertThrows(AppException.class, () -> postServiceImpl.getPostDetail(1L));

		assertEquals(1002, exception.getErrorCode().getCode());
		assertEquals("post not existed", exception.getMessage());
	}

	@Test
	void GetPost_validRequest_NotRight_fail() {
		var authentication = mock(Authentication.class);
		var securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		UserInfo user2 = new UserInfo();
		user2.setId(2L);

		Post post = new Post();
		post.setPrivacy(3);
		post.setId(1L);
		post.setUserInfo(user2);

		UserInfo user = new UserInfo();
		user.setId(1L);

		when(postRepository.findOneById(any())).thenReturn(post);
		when(userInfoRepository.findByUsername(any())).thenReturn(Optional.of(user));

		AppException exception = assertThrows(AppException.class, () -> postServiceImpl.getPostDetail(1L));

		assertEquals(1002, exception.getErrorCode().getCode());
		assertEquals("post not existed", exception.getMessage());
	}

	@Test
	void GetUserAllPost_validRequest_success() {
		var authentication = mock(Authentication.class);
		var securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);

		UserInfo user = new UserInfo();
		user.setId(1L);

		List<Post> listUser = new ArrayList<>();

		Page<Post> userInfo = new PageImpl<>(listUser);
		var pageable = PageRequest.of(0, 10);
		when(userInfoRepository.findOneById(1L)).thenReturn(user);
		when(postRepository.findByUserInfo(user, pageable)).thenReturn(userInfo);
		when(userInfoRepository.findByUsername(any())).thenReturn(Optional.of(user));

		var result = postServiceImpl.getUserAllPost(1L, 0);
	}

	@Test
	void GetUserAllPost_validRequest_NotExistUser_fail() {

		when(userInfoRepository.findOneById(1L)).thenReturn(null);

		AppException exception = assertThrows(AppException.class, () -> postServiceImpl.getUserAllPost(1L, 0));

		assertEquals(1002, exception.getErrorCode().getCode());
		assertEquals("User not existed", exception.getMessage());
	}
	
	@Test
	void GetAllPostByKeyword_validRequest_success() {
		var authentication = mock(Authentication.class);
		var securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);

		UserInfo user = new UserInfo();
		user.setId(1L);

		List<Post> listUser = new ArrayList<>();

		Page<Post> userInfo = new PageImpl<>(listUser);
		var pageable = PageRequest.of(0, 10);
		when(postRepository.findByContentContains("do", pageable)).thenReturn(userInfo);
		when(userInfoRepository.findByUsername(any())).thenReturn(Optional.of(user));

		var result = postServiceImpl.getAllPostByKeyword(0, "do");
	}
	
	@Test
	void GetAllPostByKeyword_ContentBlank_validRequest_fail() {
		var authentication = mock(Authentication.class);
		var securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);

		UserInfo user = new UserInfo();
		user.setId(1L);

		List<Post> listUser = new ArrayList<>();

		Page<Post> userInfo = new PageImpl<>(listUser);
		var pageable = PageRequest.of(0, 10);
		when(postRepository.findAll( pageable)).thenReturn(userInfo);
		when(userInfoRepository.findByUsername(any())).thenReturn(Optional.of(user));

		var result = postServiceImpl.getAllPostByKeyword(0, "");
	}
	
	@Test
	void UpdatePrivacy_validRequest_success() {
		var authentication = mock(Authentication.class);
		var securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		Post post = new Post();
		post.setContent("post test");
		post.setPrivacy(3);
		
		UserInfo user = new UserInfo();
		user.setId(1L);
		when(userInfoRepository.findByUsername(any())).thenReturn(Optional.of(user));
		when(postRepository.findByUserInfoAndId(user, 1L)).thenReturn(post);
		
		PostPostResDto resDto = new PostPostResDto();
		resDto.setContent("post test");
		resDto.setPrivacy(1);
		when(postResponseUtils.convert(post)).thenReturn(resDto);
		PostPrivacyPutReqDto reqDto = new PostPrivacyPutReqDto();
		reqDto.setPrivacy(1);
		
		var result = postServiceImpl.updatePrivacy(1L, reqDto);
		assertEquals(1, result.getPrivacy());
		assertEquals("post test", result.getContent());
	}
	
	@Test
	void UpdatePrivacy_validRequest_fail() {
		var authentication = mock(Authentication.class);
		var securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		Post post = new Post();
		post.setContent("post test");
		post.setPrivacy(3);
		
		UserInfo user = new UserInfo();
		user.setId(1L);
		when(userInfoRepository.findByUsername(any())).thenReturn(Optional.of(user));
		when(postRepository.findByUserInfoAndId(user, 1L)).thenReturn(null);
		
		PostPrivacyPutReqDto reqDto = new PostPrivacyPutReqDto();
		reqDto.setPrivacy(1);
		AppException exception = assertThrows(AppException.class, () -> postServiceImpl.updatePrivacy(1L, reqDto));

		assertEquals(1002, exception.getErrorCode().getCode());
		assertEquals("post not existed", exception.getMessage());

	}

}
