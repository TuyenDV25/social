package com.example.social_network.dto.utils.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.social_network.dto.comment.CommentResDto;
import com.example.social_network.dto.image.ImageResDto;
import com.example.social_network.dto.like.LikeResDto;
import com.example.social_network.dto.user.UserInforResDto;
import com.example.social_network.dto.utils.like.LikeResponseUtils;
import com.example.social_network.dto.utils.user.UserInfoResponseUtils;
import com.example.social_network.entity.Comment;
import com.example.social_network.entity.Image;
import com.example.social_network.entity.Like;
import com.example.social_network.entity.UserInfo;
import com.example.social_network.mapper.comment.CommentResponseMapper;
import com.example.social_network.mapper.image.ImageResponseMapper;

@ExtendWith(MockitoExtension.class)
public class CommentResponseUtilsTest {

	@Mock
	private ImageResponseMapper imageMapper;

	@Mock
	private CommentResponseMapper commentMapper;

	@Mock
	private UserInfoResponseUtils userInfoResponseUtils;

	@Mock
	private LikeResponseUtils likeResponseUtils;

	private CommentResponseUtils commentResponseUtils;

	@BeforeEach
	public void setUp() {
		commentResponseUtils = new CommentResponseUtils(imageMapper, commentMapper, userInfoResponseUtils,
				likeResponseUtils);
	}
	
	@Test
	void convert_validRequest_success() {
		Comment comment = new Comment();
		comment.setContent("day là comment");
		comment.setCreatedDate(new Date());
		comment.setUpdateDate(new Date());
		comment.setDeleted(false);
		comment.setId(1L);
		comment.setImage(new Image());
		
		
		List<LikeResDto> listLikeDto = new ArrayList<>();
		LikeResDto likeDto = new LikeResDto();
		UserInforResDto userDto = new UserInforResDto();
		userDto.setFirstName("DVT");
		likeDto.setUserInfo(userDto);
		listLikeDto.add(likeDto);
		
		Image image = new Image();
		image.setId(1L);
		image.setLinkImage("abc.com");
		comment.setImage(image);
		
		ImageResDto imageDto = new ImageResDto();
		imageDto.setId(1L);
		imageDto.setLinkImage("abc.com");
		
		UserInfo userInfo = new UserInfo();
		userInfo.setId(1L);
		userInfo.setLastName("Do");
		
		List<Like> listLike = new ArrayList<>();
		Like like = new Like();
		like.setUserInfo(userInfo);
		listLike.add(like);
		comment.setLikes(listLike);
		comment.setUser(userInfo);
		
		CommentResDto resDto = new CommentResDto();
		
		when(commentMapper.entityToDto(comment)).thenReturn(resDto);
		when(imageMapper.entityToDto(comment.getImage())).thenReturn(imageDto);
		when(likeResponseUtils.convert(comment.getLikes())).thenReturn(listLikeDto);
		when(userInfoResponseUtils.convert(comment.getUser())).thenReturn(userDto);
		
		var result = commentResponseUtils.convert(comment);
		
		assertEquals("day là comment", result.getContent());
		assertEquals(1L, result.getImage().getId());
		assertEquals("abc.com", result.getImage().getLinkImage());
		assertEquals("DVT", result.getLikes().get(0).getUserInfo().getFirstName());
		assertEquals("DVT", result.getUserInfo().getFirstName());
	}
	
	@Test
	void convertList_validRequest_success() {
		List<Comment> commentList = new ArrayList<>();
		Comment comment = new Comment();
		comment.setContent("day là comment");
		comment.setCreatedDate(new Date());
		comment.setUpdateDate(new Date());
		comment.setDeleted(false);
		comment.setId(1L);
		comment.setImage(new Image());
		
		
		List<LikeResDto> listLikeDto = new ArrayList<>();
		LikeResDto likeDto = new LikeResDto();
		UserInforResDto userDto = new UserInforResDto();
		userDto.setFirstName("DVT");
		likeDto.setUserInfo(userDto);
		listLikeDto.add(likeDto);
		
		Image image = new Image();
		image.setId(1L);
		image.setLinkImage("abc.com");
		comment.setImage(image);
		
		ImageResDto imageDto = new ImageResDto();
		imageDto.setId(1L);
		imageDto.setLinkImage("abc.com");
		
		UserInfo userInfo = new UserInfo();
		userInfo.setId(1L);
		userInfo.setLastName("Do");
		
		List<Like> listLike = new ArrayList<>();
		Like like = new Like();
		like.setUserInfo(userInfo);
		listLike.add(like);
		comment.setLikes(listLike);
		comment.setUser(userInfo);
		commentList.add(comment);
		
		when(imageMapper.entityToDto(comment.getImage())).thenReturn(imageDto);
		when(likeResponseUtils.convert(comment.getLikes())).thenReturn(listLikeDto);
		when(userInfoResponseUtils.convert(comment.getUser())).thenReturn(userDto);
		
		var result = commentResponseUtils.convert(commentList);
		
		assertEquals("day là comment", result.get(0).getContent());
		assertEquals(1L, result.get(0).getImage().getId());
		assertEquals("abc.com", result.get(0).getImage().getLinkImage());
		assertEquals("DVT", result.get(0).getLikes().get(0).getUserInfo().getFirstName());
		assertEquals("DVT", result.get(0).getUserInfo().getFirstName());
	}

}
