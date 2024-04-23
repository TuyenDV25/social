package com.example.social_network.dto.friend;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.social_network.dto.user.UserInforResDto;

@ExtendWith(MockitoExtension.class)
public class FriendRequestResDtoTest {

	@Test
	void testGetterAndSetter() {
		var result = new FriendRequestResDto();

		UserInforResDto userInfo = new UserInforResDto();
		userInfo.setFirstName("Tuyen");
		result.setUserInfo(userInfo);

		assertEquals("Tuyen", result.getUserInfo().getFirstName());
	}
}
