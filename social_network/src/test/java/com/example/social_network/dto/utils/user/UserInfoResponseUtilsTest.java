package com.example.social_network.dto.utils.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.social_network.dto.image.ImageResDto;
import com.example.social_network.dto.user.UserInforResDto;
import com.example.social_network.entity.Image;
import com.example.social_network.entity.UserInfo;
import com.example.social_network.mapper.image.ImageResponseMapper;
import com.example.social_network.mapper.user.UserMapper;

@ExtendWith(MockitoExtension.class)
public class UserInfoResponseUtilsTest {

	@Mock
	private UserMapper userMapper;

	@Mock
	private ImageResponseMapper imageMapper;

	private UserInfoResponseUtils userInfoResponseUtils;

	@BeforeEach
	public void setUp() {
		userInfoResponseUtils = new UserInfoResponseUtils(userMapper, imageMapper);
	}
	
	@Test
	void convert_validRequest_success() {
		UserInforResDto userDto = new UserInforResDto();
		userDto.setId(1L);
		userDto.setLastName("Dov");
		userDto.setFirstName("Tuyen");
		
		ImageResDto imageDto = new ImageResDto();
		imageDto.setId(1L);
		imageDto.setLinkImage("abc.com");
		List<ImageResDto> imadeListDto = new ArrayList<ImageResDto>();
		imadeListDto.add(imageDto);
		
		
		Image image = new Image();
		image.setId(1L);
		image.setLinkImage("abc.com");
		List<Image> imageList = new ArrayList<Image>();
		imageList.add(image);
		
		UserInfo user = new UserInfo();
		user.setId(1L);
		user.setLastName("Dov");
		user.setFirstName("Tuyen");
		user.setAvatarImage(imageList);
		
		
		when(userMapper.entityToDto(user)).thenReturn(userDto);
		when(imageMapper.entityToDto(user.getAvatarImage().get(0))).thenReturn(imageDto);
		
		var result = userInfoResponseUtils.convert(user);
		
		assertEquals(1L, result.getId());
		assertEquals("Dov", result.getLastName());
		assertEquals("Tuyen", result.getFirstName());
		assertEquals(1L, result.getAvatar().getId());
		assertEquals("abc.com", result.getAvatar().getLinkImage());
	}
	
	@Test
	void convertList_validRequest_success() {
		UserInforResDto userDto = new UserInforResDto();
		userDto.setId(1L);
		userDto.setLastName("Dov");
		userDto.setFirstName("Tuyen");
		
		ImageResDto imageDto = new ImageResDto();
		imageDto.setId(1L);
		imageDto.setLinkImage("abc.com");
		List<ImageResDto> imadeListDto = new ArrayList<ImageResDto>();
		imadeListDto.add(imageDto);
		
		
		Image image = new Image();
		image.setId(1L);
		image.setLinkImage("abc.com");
		List<Image> imageList = new ArrayList<Image>();
		imageList.add(image);
		
		UserInfo user = new UserInfo();
		user.setId(1L);
		user.setLastName("Dov");
		user.setFirstName("Tuyen");
		user.setAvatarImage(imageList);
		
		List<UserInfo> userList = new ArrayList<UserInfo>();
		userList.add(user);
		
		
		when(userMapper.entityToDto(user)).thenReturn(userDto);
		when(imageMapper.entityToDto(user.getAvatarImage().get(0))).thenReturn(imageDto);
		
		var result = userInfoResponseUtils.convert(userList);
		
		assertEquals(1L, result.get(0).getId());
		assertEquals("Dov", result.get(0).getLastName());
		assertEquals("Tuyen", result.get(0).getFirstName());
		assertEquals(1L, result.get(0).getAvatar().getId());
		assertEquals("abc.com", result.get(0).getAvatar().getLinkImage());
	}

}
