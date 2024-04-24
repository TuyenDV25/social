package com.example.social_network.dto.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

@ExtendWith(MockitoExtension.class)
public class CommentListResDtoTest {
	@Test
	void testGetterSetter() {
		List<CommentResDto> listUser = new ArrayList<>();

		Page<CommentResDto> userInfo = new PageImpl<>(listUser);

		CommentListResDto result = new CommentListResDto();
		result.setListComment(userInfo);
		assertEquals(0, result.getListComment().getSize());
	}
}
