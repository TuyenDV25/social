package com.example.social_network.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import com.example.social_network.dto.user.UserInfoPutReqDto;
import com.example.social_network.dto.user.UserInforResDto;
import com.example.social_network.dto.utils.user.UserInfoResponseUtils;
import com.example.social_network.entity.Image;
import com.example.social_network.entity.UserInfo;
import com.example.social_network.repository.UserInfoRepository;
import com.example.social_network.service.FileService;
import com.example.social_network.service.ImageService;
import com.example.social_network.utils.Utils;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

	@Mock
	private UserInfoRepository userInfoRepository;

	@Mock
	private UserInfoResponseUtils userInfoResponseUtils;

	@Mock
	private ImageService imageService;

	@Mock
	private FileService fileService;

	private UserServiceImpl userServiceImpl;

	@BeforeEach
	public void setUp() {
		userServiceImpl = new UserServiceImpl(userInfoRepository, userInfoResponseUtils, imageService, fileService);
	}

	@Test
	void updateInfo_validRequest_success() {
		var authentication = mock(Authentication.class);
		var securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);

		UserInfoPutReqDto reqDto = new UserInfoPutReqDto();
		reqDto.setLastName("Tester");
		reqDto.setFirstName("Dov");
		reqDto.setBirthDay("19991111");
		reqDto.setIntroyourself("minh ten a");
		reqDto.setGender("1");
		reqDto.setIdHomeTown(1);
		UserInforResDto user = new UserInforResDto();
		user.setLastName("Tester");
		user.setFirstName("Dov");
		user.setDob(Utils.convertStringToLocalDate("19991111"));
		user.setIntroyourself("minh ten a");
		user.setGender(true);
		user.setAvatar(ImageResDto.builder().id(1).linkImage("abc.com").build());

		UserInfo userTest = new UserInfo();

		when(userInfoRepository.findByUsername(any())).thenReturn(Optional.of(userTest));

		MultipartFile[] file = { mock(MultipartFile.class) };
		var listImage = new ArrayList<ImageResDto>();
		listImage.add(ImageResDto.builder().id(1).linkImage("abc.com").build());
		when(fileService.uploadImage(any())).thenReturn(listImage);
		when(imageService.findOneById(any())).thenReturn(mock(Image.class));
		when(userInfoResponseUtils.convert(userTest)).thenReturn(user);

		var result = userServiceImpl.updateInfo(reqDto, file);
		assertEquals("Tester", result.getLastName());
		assertEquals("Dov", result.getFirstName());
		assertEquals(Utils.convertStringToLocalDate("19991111"), result.getDob());
		assertEquals("minh ten a", result.getIntroyourself());
		assertEquals(1L, result.getAvatar().getId());
		assertEquals("abc.com", result.getAvatar().getLinkImage());

		verify(imageService, times(1)).save(any());
		verify(userInfoRepository, times(1)).save(any());
	}

	@Test
	void getInformation_validRequest_success() {
		var authentication = mock(Authentication.class);
		var securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);

		UserInforResDto user = new UserInforResDto();
		user.setLastName("Tester");
		user.setFirstName("Dov");
		user.setDob(Utils.convertStringToLocalDate("19991111"));
		user.setIntroyourself("minh ten a");
		user.setGender(true);
		user.setAvatar(new ImageResDto());

		UserInfo userTest = new UserInfo();
		userTest.setLastName("Tester");
		userTest.setFirstName("Dov");
		userTest.setDob(Utils.convertStringToLocalDate("19991111"));
		userTest.setIntroyourself("minh ten a");
		userTest.setGender(true);
		userTest.setAvatarImage(new ArrayList<Image>());

		when(userInfoRepository.findByUsername(any())).thenReturn(Optional.of(userTest));

		when(userInfoResponseUtils.convert(userTest)).thenReturn(user);

		var result = userServiceImpl.getUserInformation();
		assertEquals("Tester", result.getLastName());
		assertEquals("Dov", result.getFirstName());
		assertEquals(Utils.convertStringToLocalDate("19991111"), result.getDob());
		assertEquals("minh ten a", result.getIntroyourself());
		assertEquals(0, result.getAvatar().getId());
	}

	@Test
	void searchByName_blank_validRequest_success() {

		var pageable = PageRequest.of(0, 10);

		List<UserInfo> listUser = new ArrayList<>();

		Page<UserInfo> userInfo = new PageImpl<>(listUser);

		when(userInfoRepository.findAll(pageable)).thenReturn(userInfo);

		var result = userServiceImpl.searchUserByName(0, "");

	}

	@Test
	void searchByName_notBlank_validRequest_success() {

		var pageable = PageRequest.of(0, 10);

		List<UserInfo> listUser = new ArrayList<>();

		Page<UserInfo> userInfo = new PageImpl<>(listUser);

		when(userInfoRepository.findAllByFirstNameContainsOrLastNameContains("cc", "cc", pageable))
				.thenReturn(userInfo);

		var result = userServiceImpl.searchUserByName(0, "cc");

	}

	@Test
	void updateUserInfo_notBlank_validRequest_success() {
		UserInfo user = new UserInfo();
		userServiceImpl.updateUserInfo(user);
	}

	@Test
	void findOneById_notBlank_validRequest_success() {
		UserInfo userTest = new UserInfo();
		userTest.setLastName("Tester");
		userTest.setFirstName("Dov");
		userTest.setDob(Utils.convertStringToLocalDate("19991111"));
		userTest.setIntroyourself("minh ten a");
		userTest.setGender(true);
		userTest.setAvatarImage(new ArrayList<Image>());
		when(userInfoRepository.findOneById(any())).thenReturn(userTest);
		userServiceImpl.findOneById(1L);

	}

}
