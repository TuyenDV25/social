package com.example.social_network.dto.friend;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

@ExtendWith(MockitoExtension.class)
public class FriendRequestListResDtoTest {

	@Test
	void testGetterSetter() {

		List<FriendRequestResDto> listUser = new ArrayList<>();

		Page<FriendRequestResDto> userInfo = new PageImpl<>(listUser);

		var result = FriendRequestListResDto.builder().listFriendRequestResDto(userInfo).build();
		
		assertEquals(0, result.getListFriendRequestResDto().getSize());
	}
}
