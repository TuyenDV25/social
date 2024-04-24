package com.example.social_network.dto.post;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

@ExtendWith(MockitoExtension.class)
public class PostListResDtoTest {

	@Test
	void testGetterSetter() {
		List<PostPostResDto> listUser = new ArrayList<>();

		Page<PostPostResDto> userInfo = new PageImpl<>(listUser);

		var result = PostListResDto.builder().listPost(userInfo).build();

		assertEquals(0, result.getListPost().getSize());
	}

}
