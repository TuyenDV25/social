package com.example.social_network.dto.friend;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.social_network.dto.user.UserInforResDto;

@ExtendWith(MockitoExtension.class)
public class FriendResDtoTest {
	
	@Test
	void testGetterSetter() {
		var result = new FriendResDto();
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 1988);
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		Date dateRepresentation = cal.getTime();
		UserInforResDto userInfo = new UserInforResDto();
		userInfo.setFirstName("hi");
		result.setCreatedDate(dateRepresentation);
		result.setUserInfo(userInfo);
		
		assertEquals(dateRepresentation, result.getCreatedDate());
		assertEquals("hi", result.getUserInfo().getFirstName());
	}

}
