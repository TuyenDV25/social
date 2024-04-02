package com.example.social_network.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.social_network.dto.auth.OtpGetReqDto;
import com.example.social_network.dto.auth.OtpGetResDto;
import com.example.social_network.dto.auth.OtpReqDto;
import com.example.social_network.dto.auth.OtpResDto;
import com.example.social_network.dto.auth.PasswordResetReqDto;
import com.example.social_network.dto.auth.PasswordResetRequestReqDto;
import com.example.social_network.dto.auth.PasswordResetRequestResDto;
import com.example.social_network.dto.auth.PasswordResetResDto;
import com.example.social_network.dto.auth.RegistUserRepDto;
import com.example.social_network.dto.auth.RegistUserResDto;
import com.example.social_network.response.BaseResponse;
import com.example.social_network.service.AuthService;
import com.example.social_network.utils.CommonConstants;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthService service;

	@PostMapping("/register")
	public BaseResponse<RegistUserResDto> register(@RequestBody @Valid RegistUserRepDto userInfo) {
		service.insertUser(userInfo);
		return BaseResponse.<RegistUserResDto>builder().message(CommonConstants.REGISTER_SUCCESS).build();
	}

	@GetMapping("/user/userProfile")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public String userProfile() {
		return "Welcome to User Profile";
	}

	@PostMapping("/generateOtp")
	public BaseResponse<OtpResDto> generateOtp(@RequestBody OtpReqDto reqDto) {
		OtpResDto restDto = service.generateOTP(reqDto);
		return BaseResponse.<OtpResDto>builder().result(restDto).message(CommonConstants.GENERATE_OTP_SUCCESS).build();
	}

	@PostMapping("/authenticate")
	public BaseResponse<OtpGetResDto> authenticate(@RequestBody OtpGetReqDto reqDto) {
		OtpGetResDto restDto = service.validateOTP(reqDto);
		return BaseResponse.<OtpGetResDto>builder().result(restDto).message(CommonConstants.SIGN_UP_SUCCESS).build();
	}

	@PostMapping("/password-reset-request")
	public BaseResponse<PasswordResetRequestResDto> resetPassword(@RequestBody PasswordResetRequestReqDto reqDto) {
		PasswordResetRequestResDto restDto = service.requestPasswordReset(reqDto);
		return BaseResponse.<PasswordResetRequestResDto>builder().result(restDto)
				.message(CommonConstants.REQUEST_RESET_PASSWORD_SUCCESS).build();
	}

	@PostMapping("/password-reset")
	public BaseResponse<PasswordResetResDto> resetNewPassword(@RequestBody PasswordResetReqDto reqDto) {
		PasswordResetResDto restDto = service.resetNewPassword(reqDto);
		return BaseResponse.<PasswordResetResDto>builder().result(restDto)
				.message(CommonConstants.RESET_PASSWORD_SUCCESS).build();
	}

}
