package com.example.social_network.dto.utils.like;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.social_network.dto.user.UserInforResDto;
import com.example.social_network.dto.utils.user.UserInfoResponseUtils;
import com.example.social_network.entity.Like;
import com.example.social_network.entity.UserInfo;

@ExtendWith(MockitoExtension.class)
public class LikeResponseUtilsTest {

	@Mock
	private UserInfoResponseUtils userInfoResponseUtils;

	private LikeResponseUtils likeResponseUtils;

	@BeforeEach
	public void setUp() {
		likeResponseUtils = new LikeResponseUtils(userInfoResponseUtils);
	}
	
	@Test
	void convert_validRequest_success() {
		UserInforResDto userDto = new UserInforResDto();
		userDto.setId(1L);
		userDto.setLastName("Dov");
		userDto.setFirstName("Tuyen");
		
		Like like = new Like();
		UserInfo user = new UserInfo();
		user.setId(1L);
		user.setLastName("Dov");
		user.setFirstName("Tuyen");
		like.setUserInfo(user);
		
		when(userInfoResponseUtils.convert(like.getUserInfo())).thenReturn(userDto);
		
		var result = likeResponseUtils.convert(like);
		
		assertEquals(1L, result.getUserInfo().getId());
		assertEquals("Dov", result.getUserInfo().getLastName());
		assertEquals("Tuyen", result.getUserInfo().getFirstName());
	}
	
	@Test
	void convertList_validRequest_success() {
		UserInforResDto userDto = new UserInforResDto();
		userDto.setId(1L);
		userDto.setLastName("Dov");
		userDto.setFirstName("Tuyen");
		
		Like like = new Like();
		UserInfo user = new UserInfo();
		user.setId(1L);
		user.setLastName("Dov");
		user.setFirstName("Tuyen");
		like.setUserInfo(user);
		
		List<Like> likeList = new ArrayList<>();
		likeList.add(like);
		
		when(userInfoResponseUtils.convert(like.getUserInfo())).thenReturn(userDto);
		
		var result = likeResponseUtils.convert(likeList);
		
		assertEquals(1L, result.get(0).getUserInfo().getId());
		assertEquals("Dov", result.get(0).getUserInfo().getLastName());
		assertEquals("Tuyen", result.get(0).getUserInfo().getFirstName());
	}
}
