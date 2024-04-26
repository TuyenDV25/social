package com.example.social_network.jwt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class JwtUtilsTest {

	private JwtUtils jwtUtils = new JwtUtils();

	@Test
	void testGenerateToken_Success() {
		String token = jwtUtils.generateToken("Dovan@gmail.com", 6000000);
		assertNotNull(token);
	}

	@Test
	void extractUsernameSuccess() {
		String token = jwtUtils.generateToken("Dovan@gmail.com", 6000000);
		String userName = jwtUtils.extractUsername(token);
		assertEquals("Dovan@gmail.com", userName);
	}

	@Test
	void extractExpiration_success() {
		String token = jwtUtils.generateToken("Dovan@gmail.com", 6000000);
		Date date = jwtUtils.extractExpiration(token);
		assertEquals(getHourOfDay(new Date(System.currentTimeMillis() + 6000000)), getHourOfDay(date));
		assertEquals(getMinutesOfDay(new Date(System.currentTimeMillis() + 6000000)), getMinutesOfDay(date));
		assertEquals(getSecondsOfDay(new Date(System.currentTimeMillis() + 6000000)), getSecondsOfDay(date));
	}

	@Test
	void validateToken_success() {
		String token = jwtUtils.generateToken("Dovan@gmail.com", 6000000);
		Boolean isOK = jwtUtils.validateJwtToken(token);
		assertEquals(true, isOK);
	}

	int getHourOfDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	int getMinutesOfDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MINUTE);
	}

	int getSecondsOfDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.SECOND);
	}
}
