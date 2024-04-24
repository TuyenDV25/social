package com.example.social_network.dto.post;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PostPostReqDtoTest {
	@Test
	void testGetterSetter() {
		PostPostReqDto req = new PostPostReqDto();
		req.setContent("abc");
		req.setPrivacy(1);

		assertEquals("abc", req.getContent());
		assertEquals(1, req.getPrivacy());
	}
}
