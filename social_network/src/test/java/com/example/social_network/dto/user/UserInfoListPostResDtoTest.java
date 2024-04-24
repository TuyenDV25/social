package com.example.social_network.dto.user;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

@ExtendWith(MockitoExtension.class)
public class UserInfoListPostResDtoTest {
	@Test
	void testGetterSetter() {
		List<UserInforResDto> listUser = new ArrayList<>();

		Page<UserInforResDto> userInfo = new PageImpl<>(listUser);

		var result = UserInfoListPostResDto.builder().listUser(userInfo).build();

		assertEquals(0, result.getListUser().getSize());
	}
}
