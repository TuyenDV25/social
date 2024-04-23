package com.example.social_network.service.impl;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.google.common.cache.LoadingCache;

@ExtendWith(MockitoExtension.class)
public class OTPServiceImplTest {

	@Mock
	LoadingCache<String, Integer> otpCache;

	private OTPServiceImpl oTPServiceImpl;

	@BeforeEach
	public void setUp() {
		oTPServiceImpl = new OTPServiceImpl();
	}

	@Test
	void generateOTP_validRequest_success() {
		int result = oTPServiceImpl.generateOTP("abcd.com.vn");
		String resultString = "" + result + "";
		assertThat(resultString).hasSize(6).containsOnlyDigits();
	}

	@Test
	void getOtp_validRequest_success() {
		int result = oTPServiceImpl.getOtp("123456");
		String resultString = "" + result + "";
		assertThat(resultString).hasSize(1).containsOnlyDigits();
	}
	
	@Test
	void clearOTP_validRequest_success() {
		oTPServiceImpl.clearOTP("abcdef");
	}

}
