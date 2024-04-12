package com.example.social_network.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.example.social_network.dto.auth.RegistUserRepDto;
import com.example.social_network.dto.user.UserInforResDto;
import com.example.social_network.response.BaseResponse;
import com.example.social_network.service.AuthService;
import com.example.social_network.utils.CommonConstants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

	@Autowired
	private AuthService service;

	@Operation(summary = "sign up")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "register succesfully", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = RegistUserRepDto.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid id account", content = @Content) })
	@PostMapping("/register")
	public BaseResponse<UserInforResDto> register(@RequestBody @Valid RegistUserRepDto userInfo) {
		UserInforResDto resDto = service.insertUser(userInfo);
		return BaseResponse.<UserInforResDto>builder()
				.result(resDto)
				.message(CommonConstants.REGISTER_SUCCESS).build();
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
	public BaseResponse<?> resetNewPassword(@RequestBody PasswordResetReqDto reqDto) {
		service.resetNewPassword(reqDto);
		return BaseResponse.builder().message(CommonConstants.RESET_PASSWORD_SUCCESS).build();
	}

}
