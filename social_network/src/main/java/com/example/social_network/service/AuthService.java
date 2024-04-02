package com.example.social_network.service;

import com.example.social_network.dto.auth.OtpGetReqDto;
import com.example.social_network.dto.auth.OtpGetResDto;
import com.example.social_network.dto.auth.OtpReqDto;
import com.example.social_network.dto.auth.OtpResDto;
import com.example.social_network.dto.auth.PasswordResetReqDto;
import com.example.social_network.dto.auth.PasswordResetRequestReqDto;
import com.example.social_network.dto.auth.PasswordResetRequestResDto;
import com.example.social_network.dto.auth.PasswordResetResDto;
import com.example.social_network.dto.auth.RegistUserRepDto;

public interface AuthService {
	public void insertUser(RegistUserRepDto reqDto);

	public PasswordResetRequestResDto requestPasswordReset(PasswordResetRequestReqDto reqDto);

	public PasswordResetResDto resetNewPassword(PasswordResetReqDto reqDto);

	public OtpResDto generateOTP(OtpReqDto reqDto);

	public OtpGetResDto validateOTP(OtpGetReqDto reqDto);
}
