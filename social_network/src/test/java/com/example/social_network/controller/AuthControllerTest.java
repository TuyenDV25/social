package com.example.social_network.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.social_network.dto.auth.OtpGetReqDto;
import com.example.social_network.dto.auth.OtpGetResDto;
import com.example.social_network.dto.auth.OtpReqDto;
import com.example.social_network.dto.auth.OtpResDto;
import com.example.social_network.dto.auth.PasswordResetReqDto;
import com.example.social_network.dto.auth.PasswordResetRequestReqDto;
import com.example.social_network.dto.auth.PasswordResetRequestResDto;
import com.example.social_network.dto.auth.RegistUserRepDto;
import com.example.social_network.dto.user.UserInforSignupResDto;
import com.example.social_network.service.AuthService;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {
	
	@Mock
	private AuthService service;
	
	private AuthController authController;
	
	@BeforeEach
	void initData() {
		authController = new AuthController(service);
	}
	
	@Test
	void register_validRequest_success() {
		RegistUserRepDto reqDto = new RegistUserRepDto();
		reqDto.setPassword("matkhau");
		reqDto.setUsername("dotest");
		UserInforSignupResDto resDto = new UserInforSignupResDto();
		resDto.setUsername("dotest");
		when(service.insertUser(reqDto)).thenReturn(resDto);
		
		var result = authController.register(reqDto);

		assertEquals(1000, result.getCode());
		assertEquals("Đăng ký tài khoản thành công", result.getMessage());
	}
	
	@Test
	void generateOtp_validRequest_success() {
		OtpReqDto reqDto = new OtpReqDto();
		reqDto.setPassword("matkharu");
		reqDto.setUserName("myname");
		OtpResDto resDto = new OtpResDto();
		resDto.setOtp(123456);
		
		when(service.generateOTP(reqDto)).thenReturn(resDto);
		
		var result = authController.generateOtp(reqDto);

		assertEquals(1000, result.getCode());
		assertEquals("signin successfully", result.getMessage());
	}
	
	@Test
	void authenticate_validRequest_success() {
		OtpGetReqDto reqDto = new OtpGetReqDto();
		reqDto.setOtp("123456");
		reqDto.setUserName("myname");
		OtpGetResDto resDto = new OtpGetResDto();
		resDto.setToken("ey12sdte543dfretret");
		
		when(service.validateOTP(reqDto)).thenReturn(resDto);
		
		var result = authController.authenticate(reqDto);

		assertEquals(1000, result.getCode());
		assertEquals("signup successfully", result.getMessage());
	}
	
	@Test
	void resetPassword_validRequest_success() {
		PasswordResetRequestReqDto reqDto = new PasswordResetRequestReqDto();
		reqDto.setUsername("TuyenDv");
		PasswordResetRequestResDto resDto = new PasswordResetRequestResDto();
		resDto.setLinkResetPassword("abcdefghijklmno.com");
		
		when(service.requestPasswordReset(reqDto)).thenReturn(resDto);
		
		var result = authController.resetPassword(reqDto);

		assertEquals(1000, result.getCode());
		assertEquals("đã gửi link tới mail để tạo mới mật khẩu", result.getMessage());
	}
	
	@Test
	void resetNewPassword_validRequest_success() {
		PasswordResetReqDto reqDto = new PasswordResetReqDto();
		reqDto.setPassword("tuyendv");
		reqDto.setToken("fsdfgehtytyyty");
		
		var result = authController.resetNewPassword(reqDto);

		assertEquals(1000, result.getCode());
		assertEquals("Tạo mới mật khẩu thành công", result.getMessage());
	}
}
