package com.example.social_network.service.impl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.social_network.dto.auth.OtpGetReqDto;
import com.example.social_network.dto.auth.OtpGetResDto;
import com.example.social_network.dto.auth.OtpReqDto;
import com.example.social_network.dto.auth.OtpResDto;
import com.example.social_network.dto.auth.PasswordResetReqDto;
import com.example.social_network.dto.auth.PasswordResetRequestReqDto;
import com.example.social_network.dto.auth.PasswordResetRequestResDto;
import com.example.social_network.dto.auth.RegistUserRepDto;
import com.example.social_network.dto.user.UserInforSignupResDto;
import com.example.social_network.entity.PasswordResetToken;
import com.example.social_network.entity.UserInfo;
import com.example.social_network.enumdef.RoleType;
import com.example.social_network.exception.AppException;
import com.example.social_network.exception.ErrorCode;
import com.example.social_network.jwt.JwtUtils;
import com.example.social_network.jwt.SecurityConstant;
import com.example.social_network.repository.PasswordResetTokenReponsitory;
import com.example.social_network.repository.UserInfoRepository;
import com.example.social_network.service.AuthService;
import com.example.social_network.service.OTPService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final UserInfoRepository userInfoRepository;

	private final PasswordResetTokenReponsitory passwordResetTokenReponsitory;

	private final JwtUtils jwtUtils;

	private final AuthenticationManager authenticationManager;

	private final ModelMapper modelMapper;

	private final OTPService otpService;

	@Override
	public UserInforSignupResDto insertUser(RegistUserRepDto reqDto) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		reqDto.setPassword(encoder.encode(reqDto.getPassword()));
		UserInfo userInfor = modelMapper.map(reqDto, UserInfo.class);
		userInfor.setRoles(RoleType.USER.name());

		// check user tồn tại
		Optional<UserInfo> existUser = userInfoRepository.findByUsername(reqDto.getUsername());
		if (existUser.isPresent()) {
			throw new AppException(ErrorCode.USER_EXISTED);
		}
		userInfoRepository.save(userInfor);
		return modelMapper.map(userInfor, UserInforSignupResDto.class);
	}

	@Override
	public PasswordResetRequestResDto requestPasswordReset(PasswordResetRequestReqDto reqDto) {
		Optional<UserInfo> userInfoEntity = userInfoRepository.findByUsername(reqDto.getUsername());

		if (!userInfoEntity.isPresent()) {
			throw new AppException(ErrorCode.SIGNIN_ERROR);
		}

		String token = jwtUtils.generateToken(userInfoEntity.get().getUsername(),
				SecurityConstant.PASSWORD_RESET_EXPIRATION_TIME);

		PasswordResetToken passwordResetTokenEntity = new PasswordResetToken();
		passwordResetTokenEntity.setToken(token);
		passwordResetTokenEntity.setUserInfo(userInfoEntity.get());

		passwordResetTokenReponsitory.save(passwordResetTokenEntity);

		PasswordResetRequestResDto resDto = new PasswordResetRequestResDto();
		resDto.setLinkResetPassword(SecurityConstant.URL_AUTH + token);

		return resDto;
	}

	@Override
	public void resetNewPassword(PasswordResetReqDto reqDto) {
		if (!jwtUtils.validateJwtToken(reqDto.getToken())) {
			throw new AppException(ErrorCode.TOKEN_RESET_PSW_WRONG);
		}
		PasswordResetToken passwordResetTokenEntity = passwordResetTokenReponsitory.findByToken(reqDto.getToken());

		if (passwordResetTokenEntity == null) {
			throw new AppException(ErrorCode.INVALID_KEY);
		}

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		UserInfo userInfo = passwordResetTokenEntity.getUserInfo();

		userInfo.setPassword(encoder.encode(reqDto.getPassword()));
		UserInfo savedUserInfo = userInfoRepository.save(userInfo);

		if (savedUserInfo == null) {
			throw new AppException(ErrorCode.INVALID_KEY);
		}

		passwordResetTokenReponsitory.delete(passwordResetTokenEntity);
	}

	@Override
	public OtpResDto generateOTP(OtpReqDto reqDto) {
		OtpResDto restDto = new OtpResDto();
		try {
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(reqDto.getUserName(), reqDto.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);

			if (authentication.isAuthenticated()) {
				int otp = otpService.generateOTP(reqDto.getUserName());
				restDto.setOtp(otp);
				return restDto;
			}
		} catch (Exception e) {
			throw new AppException(ErrorCode.SIGNIN_ERROR);
		}
		return restDto;
	}

	@Override
	public OtpGetResDto validateOTP(OtpGetReqDto reqDto) {
		OtpGetResDto resDto = new OtpGetResDto();
		int serverOtp = otpService.getOtp(reqDto.getUserName());
		if (serverOtp > 0) {
			if (Integer.valueOf(reqDto.getOtp()) == serverOtp) {
				otpService.clearOTP(reqDto.getUserName());
				resDto.setToken(jwtUtils.generateToken(reqDto.getUserName(), SecurityConstant.EXPIRATION_TIME));
				return resDto;
			} else {
				throw new AppException(ErrorCode.OTP_INVALID);
			}
		} else {
			throw new AppException(ErrorCode.OTP_INVALID);
		}
	}
}
