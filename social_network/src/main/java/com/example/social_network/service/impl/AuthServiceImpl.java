package com.example.social_network.service.impl;

import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.social_network.dto.auth.OtpGetReqDto;
import com.example.social_network.dto.auth.OtpGetResDto;
import com.example.social_network.dto.auth.OtpReqDto;
import com.example.social_network.dto.auth.OtpResDto;
import com.example.social_network.dto.auth.PasswordResetReqDto;
import com.example.social_network.dto.auth.PasswordResetRequestReqDto;
import com.example.social_network.dto.auth.PasswordResetRequestResDto;
import com.example.social_network.dto.auth.PasswordResetResDto;
import com.example.social_network.dto.auth.RegistUserRepDto;
import com.example.social_network.entity.PasswordResetToken;
import com.example.social_network.entity.UserInfo;
import com.example.social_network.exception.AppException;
import com.example.social_network.exception.ErrorCode;
import com.example.social_network.jwt.JwtUtils;
import com.example.social_network.jwt.SecurityConstant;
import com.example.social_network.repository.PasswordResetTokenReponsitory;
import com.example.social_network.repository.UserInfoRepository;
import com.example.social_network.service.AuthService;
import com.example.social_network.service.OTPService;
import com.example.social_network.utils.CommonConstants;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private UserInfoRepository userInfoRepository;

	@Autowired
	private PasswordResetTokenReponsitory passwordResetTokenReponsitory;

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private OTPService otpService;

	@Override
	public void insertUser(RegistUserRepDto reqDto) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		reqDto.setPassword(encoder.encode(reqDto.getPassword()));
		UserInfo userInfor = modelMapper.map(reqDto, UserInfo.class);

		// check user tồn tại
		UserInfo existUser = userInfoRepository.findByUsername(reqDto.getUsername()).orElse(null);
		if (Objects.nonNull(existUser)) {
			throw new AppException(ErrorCode.USER_EXISTED);
		}
		userInfoRepository.save(userInfor);
	}

	@Override
	public PasswordResetRequestResDto requestPasswordReset(PasswordResetRequestReqDto reqDto) {
		UserInfo userInfoEntity = userInfoRepository.findByUsername(reqDto.getEmail())
				.orElseThrow(() -> new UsernameNotFoundException(CommonConstants.USER_NOT_FOUND));

		String token = jwtUtils.generateToken(userInfoEntity.getUsername(),
				SecurityConstant.PASSWORD_RESET_EXPIRATION_TIME);

		PasswordResetToken passwordResetTokenEntity = new PasswordResetToken();
		passwordResetTokenEntity.setToken(token);
		passwordResetTokenEntity.setUserInfo(userInfoEntity);

		passwordResetTokenReponsitory.save(passwordResetTokenEntity);

		PasswordResetRequestResDto resDto = new PasswordResetRequestResDto();
		resDto.setLinkResetPassword(SecurityConstant.URL_AUTH + token);

		return resDto;
	}

	@Override
	public PasswordResetResDto resetNewPassword(PasswordResetReqDto reqDto) {
		if (jwtUtils.isTokenExpired(reqDto.getToken())) {
			throw new AppException(ErrorCode.TOKEN_EXPIRED);
		}
		PasswordResetToken passwordResetTokenEntity = passwordResetTokenReponsitory.findByToken(reqDto.getToken());

		if (passwordResetTokenEntity == null) {
			throw new AppException(ErrorCode.INVALID_KEY);
		}

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		UserInfo userInfo = passwordResetTokenEntity.getUserInfo();
		String password = encoder.encode(reqDto.getPassword());
		userInfo.setPassword(password);
		UserInfo savedUserInfo = userInfoRepository.save(userInfo);

		if (savedUserInfo == null || !savedUserInfo.getPassword().equalsIgnoreCase(password)) {
			throw new AppException(ErrorCode.INVALID_KEY);
		}

		passwordResetTokenReponsitory.delete(passwordResetTokenEntity);

		return new PasswordResetResDto();
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
			throw new AppException(ErrorCode.USER_NOT_EXISTED);
		}
		return restDto;
	}

	@Override
	public OtpGetResDto validateOTP(OtpGetReqDto reqDto) {
		OtpGetResDto resDto = new OtpGetResDto();
		int serverOtp = otpService.getOtp(reqDto.getUserName());
		if (serverOtp > 0) {
			if (reqDto.getOtp() == serverOtp) {
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
