package com.example.social_network.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

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
import com.example.social_network.exception.AppException;
import com.example.social_network.jwt.JwtUtils;
import com.example.social_network.jwt.SecurityConstant;
import com.example.social_network.repository.PasswordResetTokenReponsitory;
import com.example.social_network.repository.UserInfoRepository;
import com.example.social_network.service.OTPService;

@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {
	
	@Mock
	private UserInfoRepository userInfoRepository;

	@Mock
	private PasswordResetTokenReponsitory passwordResetTokenReponsitory;

	@Mock
	private JwtUtils jwtUtils;

	@Mock
	private AuthenticationManager authenticationManager;

	@Mock
	private ModelMapper modelMapper;

	@Mock
	private OTPService otpService;
	
	private AuthServiceImpl authServiceImpl;
	
	@BeforeEach
	public void setUp() {
		authServiceImpl = new AuthServiceImpl(userInfoRepository, passwordResetTokenReponsitory, jwtUtils,
				authenticationManager, modelMapper, otpService);
	}
	
	@Test
	void insertUser_validRequest_success() {
		RegistUserRepDto reqDto = new RegistUserRepDto();
		reqDto.setPassword("matkhau");
		reqDto.setUsername("tuyen");
		
		UserInfo user = new UserInfo();
		user.setUsername("tuyen");
		user.setPassword("matkhau");
		
		when(modelMapper.map(reqDto, UserInfo.class)).thenReturn(user);
		
		Optional<UserInfo> chosen = Optional.empty(); 
		when(userInfoRepository.findByUsername(reqDto.getUsername())).thenReturn(chosen);
		
		UserInforSignupResDto resDto = new UserInforSignupResDto();
		resDto.setUsername("tuyen");
		when(modelMapper.map(user, UserInforSignupResDto.class)).thenReturn(resDto);
		
		var result = authServiceImpl.insertUser(reqDto);
		
		assertEquals("tuyen", result.getUsername());
	}
	
	@Test
	void insertUser_validRequest_fail() {
		RegistUserRepDto reqDto = new RegistUserRepDto();
		reqDto.setPassword("matkhau");
		reqDto.setUsername("tuyen");
		
		UserInfo user = new UserInfo();
		user.setUsername("tuyen");
		user.setPassword("matkhau");
		
		when(modelMapper.map(reqDto, UserInfo.class)).thenReturn(user);
		
		UserInfo user2 = new UserInfo();
		user2.setUsername("tuyen");
		user2.setPassword("matkhau");
		
		when(userInfoRepository.findByUsername(reqDto.getUsername())).thenReturn(Optional.of(user2));
		
		AppException exception = assertThrows(AppException.class,
				() -> authServiceImpl.insertUser( reqDto));

		assertEquals(1002, exception.getErrorCode().getCode());
		assertEquals("User existed", exception.getMessage());
	}
	
	@Test
	void requestPasswordReset_validRequest_success() {
		PasswordResetRequestReqDto reqDto = new PasswordResetRequestReqDto();
		reqDto.setUsername("tuyen");
		UserInfo user2 = new UserInfo();
		user2.setUsername("tuyen");
		user2.setPassword("matkhau");
		
		when(userInfoRepository.findByUsername(reqDto.getUsername())).thenReturn(Optional.of(user2));
		String token = "tokenvalidate";
		when(jwtUtils.generateToken(user2.getUsername(), 1200000)).thenReturn(token);
		
		var result = authServiceImpl.requestPasswordReset(reqDto);
		assertEquals("http://localhost:8080/api/v1/auth/tokenvalidate", result.getLinkResetPassword());
		
	}
	
	@Test
	void requestPasswordReset_validRequest_fail() {
		PasswordResetRequestReqDto reqDto = new PasswordResetRequestReqDto();
		reqDto.setUsername("tuyen");

		Optional<UserInfo> chosen = Optional.empty(); 
		when(userInfoRepository.findByUsername(reqDto.getUsername())).thenReturn(chosen);

		AppException exception = assertThrows(AppException.class,
				() -> authServiceImpl.requestPasswordReset( reqDto));

		assertEquals(1008, exception.getErrorCode().getCode());
		assertEquals("User not existed", exception.getMessage());
	}
	
	@Test
	void resetNewPassword_validRequest_success() {
		PasswordResetReqDto reqDto = new PasswordResetReqDto();
		reqDto.setPassword("matkhaumoi");
		reqDto.setToken("etuy");
		when(jwtUtils.validateJwtToken(reqDto.getToken())).thenReturn(true);
		
		PasswordResetToken pass = new PasswordResetToken();
		pass.setId(1L);
		pass.setToken("etuy");
		UserInfo user = new UserInfo();
		user.setFirstName("tuỳen");
		user.setGender(true);
		user.setUsername("dovana@gmail.com");
		user.setPassword("mkthoi");
		pass.setUserInfo(user);
		when(passwordResetTokenReponsitory.findByToken(reqDto.getToken())).thenReturn(pass);
		
		UserInfo user2 = new UserInfo();
		user2.setFirstName("tuỳen");
		user2.setGender(true);
		user2.setUsername("dovana@gmail.com");
		user2.setPassword("$2a$12$30hCNoq4PQR5LodeMBGzNuEzjlGlvtgFktFw2aBZAtMtXqLDcSWea");
		when(userInfoRepository.save(user)).thenReturn(user2);
		
		authServiceImpl.resetNewPassword(reqDto);
	}
	
	@Test
	void resetNewPassword_validRequest_tokenExp_success() {
		PasswordResetReqDto reqDto = new PasswordResetReqDto();
		reqDto.setPassword("matkhaumoi");
		reqDto.setToken("etuy");
		when(jwtUtils.validateJwtToken(reqDto.getToken())).thenReturn(false);
		AppException exception = assertThrows(AppException.class,
				() -> authServiceImpl.resetNewPassword( reqDto));

		assertEquals(1008, exception.getErrorCode().getCode());
		assertEquals("have error reset password", exception.getMessage());
	}
	
	@Test
	void resetNewPassword_validRequest_tokenWrong_fail() {
		PasswordResetReqDto reqDto = new PasswordResetReqDto();
		reqDto.setPassword("matkhaumoi");
		reqDto.setToken("etuy");
		when(jwtUtils.validateJwtToken(reqDto.getToken())).thenReturn(false);
		
		AppException exception = assertThrows(AppException.class,
				() -> authServiceImpl.resetNewPassword( reqDto));

		assertEquals(1008, exception.getErrorCode().getCode());
		assertEquals("have error reset password", exception.getMessage());
	}
	
	@Test
	void generateOTP_validRequest_success() {
		Authentication authentication = new Authentication() {
			
			@Override
			public String getName() {
				return null;
			}
			
			@Override
			public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
			}
			
			@Override
			public boolean isAuthenticated() {
				return true;
			}
			
			@Override
			public Object getPrincipal() {
				return null;
			}
			
			@Override
			public Object getDetails() {
				return null;
			}
			
			@Override
			public Object getCredentials() {
				return null;
			}
			
			@Override
			public Collection<? extends GrantedAuthority> getAuthorities() {
				return null;
			}
		};
		authentication.setAuthenticated(true);
		OtpReqDto reqDto = new OtpReqDto();
		reqDto.setUserName("tuyendv");
		reqDto.setPassword("passxx");
		when(authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(reqDto.getUserName(), reqDto.getPassword()))).thenReturn(authentication);
		when(otpService.generateOTP(reqDto.getUserName())).thenReturn(123456);
		
		var result = authServiceImpl.generateOTP(reqDto);
		assertEquals(123456, result.getOtp());
	}
	
	@Test
	void validateOTP_validRequest_success() {
		OtpGetReqDto reqDto = new OtpGetReqDto();
		reqDto.setOtp("123456");
		reqDto.setUserName("dvt@gmail.vn");
		when(otpService.getOtp(reqDto.getUserName())).thenReturn(123456);
		when(jwtUtils.generateToken(reqDto.getUserName(), SecurityConstant.EXPIRATION_TIME)).thenReturn("tee");
		
		OtpGetResDto resDto = new OtpGetResDto();
		resDto.setToken("tee");
		
		var result = authServiceImpl.validateOTP(reqDto);
		assertEquals("tee", result.getToken());
	}

}
