package com.example.social_network.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.multipart.MultipartFile;

import com.example.social_network.dto.user.UserInfoPutReqDto;
import com.example.social_network.dto.user.UserInforResDto;
import com.example.social_network.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

	@Mock
	private UserService userService;

	private UserController userController;

	@BeforeEach
	void initData() {
		userController = new UserController(userService);
	}

	@Test
	void UpdateInfo_validRequest_success() throws Exception {

		when(userService.updateInfo(any(), any())).thenReturn(mock(UserInforResDto.class));
		
		var input = new UserInfoPutReqDto();
		MultipartFile[] file = {mock(MultipartFile.class)};
		
		var result = userController.updateInfo(input, file);
		
		assertEquals(1000, result.getCode());
		assertEquals("update user successfully",result.getMessage());

	}
	
	@Test
	void detail_signinUser_validRequest_success() throws Exception {

		when(userService.getUserInformation()).thenReturn(mock(UserInforResDto.class));
		
		var result = userController.getInformation();
		
		assertEquals(1000, result.getCode());
		assertEquals("get detail user successfully",result.getMessage());

	}
	
	@Test
	void detail_userById_validRequest_success() throws Exception {

		when(userService.findDetailUser(any())).thenReturn(mock(UserInforResDto.class));
		
		var result = userController.getInformationById(1L);
		
		assertEquals(1000, result.getCode());
		assertEquals("get detail user successfully",result.getMessage());

	}
	
	@Test
	void search_validRequest_success() throws Exception {
		
		List<UserInforResDto> listUser = new ArrayList<>();
		
		Page<UserInforResDto> userInfo = new PageImpl<>(listUser);
 
		when(userService.searchUserByName(any(), any())).thenReturn(userInfo);
		
		var result = userController.searchUser(1, "");
		
		assertEquals(1000, result.getCode());
		assertEquals("search user successfully",result.getMessage());

	}
}
