package com.example.social_network.dto.user;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserInforResDtoTest {

	@Test
	void testGetterSetter() {
		UserInforResDto u = new UserInforResDto();
		u.setUsername("gamil.com");
		u.setGender(false);
		Calendar calendar = Calendar.getInstance();
		calendar.set(2018, 11, 31);
		Date happyNewYearDate = calendar.getTime();
		u.setCreatedDate(happyNewYearDate);
		u.setUpdateDate(happyNewYearDate);
		assertEquals("gamil.com", u.getUsername());
		assertEquals(false, u.isGender());
		assertEquals(happyNewYearDate, u.getCreatedDate());
		assertEquals(happyNewYearDate, u.getUpdateDate());
	}
}
