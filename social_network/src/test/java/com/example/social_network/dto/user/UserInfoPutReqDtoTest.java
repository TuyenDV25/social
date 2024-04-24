package com.example.social_network.dto.user;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserInfoPutReqDtoTest {

	@Test
	void testGetterSetter() {
		UserInfoPutReqDto u = new UserInfoPutReqDto();
		u.setIdHomeTown(1);
		u.setIdCurrentCity(1);
		assertEquals(1, u.getIdCurrentCity());
		assertEquals(1, u.getIdHomeTown());
	}
}
