package com.example.social_network.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserInfoTest {

	@Test
	void testGetterSetter() {
		UserInfo user = new UserInfo();
		user.setPassword("matkhaunha");
		user.setRoles("User");
		user.setLastName("last");
		user.setFirstName("first");
		user.setDob(LocalDate.of(1992,11,25));
		user.setIntroyourself("thichgame");
		user.setGender(true);
		PasswordResetToken ab = new PasswordResetToken();
		ab.setToken("vai");
		user.setPasswordResetToken(ab);
		
		Post po = new Post();
		po.setContent("this is a post");
		List<Post> postList = new ArrayList<>();
		postList.add(po);
		user.setPosts(postList);
		
		Like like = new Like();
		like.setId(1L);
		List<Like> likeList = new ArrayList<>();
		likeList.add(like);
		
		user.setLikes(likeList);
		
		assertEquals("matkhaunha", user.getPassword());
		assertEquals("User", user.getRoles());
		assertEquals("last", user.getLastName());
		assertEquals("first", user.getFirstName());
		assertEquals(LocalDate.of(1992,11,25), user.getDob());
		assertEquals("thichgame", user.getIntroyourself());
		assertEquals("vai", user.getPasswordResetToken().getToken());
		assertEquals("this is a post", user.getPosts().get(0).getContent());
		assertEquals(1L, user.getLikes().get(0).getId());
	}
}
