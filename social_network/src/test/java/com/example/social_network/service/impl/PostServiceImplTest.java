package com.example.social_network.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;

import com.example.social_network.dto.image.ImageResDto;
import com.example.social_network.dto.post.PostPostReqDto;
import com.example.social_network.dto.post.PostPostResDto;
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
	void updateInfo_validRequest_success() {
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
		
		PostPostResDto resDto = new PostPostResDto();
		resDto.setContent("post test");
		resDto.setImage(listImage);
		resDto.setPrivacy(1);
		
		when(postResponseUtils.convert(post)).thenReturn(resDto);
		
		MultipartFile[] file = { mock(MultipartFile.class) };
		
		var result = postServiceImpl.createPost(reqDto, file);
		assertEquals(1, result.getPrivacy());
		assertEquals("post test", result.getContent());
		assertEquals(listImage, result.getImage());
	
		verify(postRepository, times(1)).save(any());
	}
	
	@Test
	void updateInfo_validRequest_fail() {
		PostPostReqDto reqDto = new PostPostReqDto();
		AppException exception = assertThrows(AppException.class, () -> postServiceImpl.createPost(reqDto, null));
		
		assertEquals(1004, exception.getErrorCode().getCode());
		assertEquals("post must have at least content or file", exception.getMessage());
	}

}
