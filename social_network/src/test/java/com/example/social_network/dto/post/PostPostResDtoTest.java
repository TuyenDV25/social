package com.example.social_network.dto.post;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PostPostResDtoTest {
	
	@Test
	void testGetterSetter() {
		PostPostResDto re = new PostPostResDto();
		re.setCommentCount(1L);
		
		assertEquals(1L, re.getCommentCount());
	}
}
