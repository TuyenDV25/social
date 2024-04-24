package com.example.social_network.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ImageTest {
	@Test
	void testGetterSetter() {
		Image ima = new Image();
		UserInfo user = new UserInfo();
		user.setFirstName("Tuyen");
		ima.setUserInfo(user);
		
		Post po = new Post();
		po.setPrivacy(3);
		ima.setPost(po);
		
		Comment comment = new Comment();
		comment.setContent("com");
		ima.setComment(comment);
		
		assertEquals("Tuyen", ima.getUserInfo().getFirstName());
		assertEquals(3, ima.getPost().getPrivacy());
		assertEquals("com", ima.getComment().getContent());
		
	}
}
