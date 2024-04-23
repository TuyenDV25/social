package com.example.social_network.dto.utils.post;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.social_network.dto.comment.CommentResDto;
import com.example.social_network.dto.image.ImageResDto;
import com.example.social_network.dto.like.LikeResDto;
import com.example.social_network.dto.post.PostPostResDto;
import com.example.social_network.dto.user.UserInforResDto;
import com.example.social_network.dto.utils.comment.CommentResponseUtils;
import com.example.social_network.dto.utils.like.LikeResponseUtils;
import com.example.social_network.dto.utils.user.UserInfoResponseUtils;
import com.example.social_network.entity.Comment;
import com.example.social_network.entity.Image;
import com.example.social_network.entity.Like;
import com.example.social_network.entity.Post;
import com.example.social_network.entity.UserInfo;
import com.example.social_network.mapper.image.ImageResponseMapper;
import com.example.social_network.mapper.post.PostResponseMapper;
import com.example.social_network.repository.CommentRepository;
import com.example.social_network.repository.LikeRepository;

@ExtendWith(MockitoExtension.class)
public class PostResponseUtilsTest {

	@Mock
	private PostResponseMapper postMapper;
	
	@Mock
	private ImageResponseMapper imageMapper;

	@Mock
	private LikeRepository likeRepository;

	@Mock
	private CommentRepository commentRepository;

	@Mock
	private CommentResponseUtils commentResponseUtils;

	@Mock
	private LikeResponseUtils likeResponseUtils;

	@Mock
	private UserInfoResponseUtils userResponseUtils;
	
	private PostResponseUtils postResponseUtils;
	
	@BeforeEach
	public void setUp() {
		postResponseUtils = new PostResponseUtils(postMapper, imageMapper, likeRepository,
				commentRepository, commentResponseUtils, likeResponseUtils, userResponseUtils);
	}
	
	@Test
	void convert_validRequest_success() {
		Post post = new Post();
		post.setId(1L);
		post.setPrivacy(3);
		post.setContent("this is a post");
		
		Comment comment = new Comment();
		comment.setContent("this is a comment");
		comment.setId(1L);
		List<Comment> commentList = new ArrayList<Comment>();
		commentList.add(comment);
		post.setComments(commentList);
		
		List<Image> imageList = new ArrayList<Image>();
		Image image = new Image();
		image.setId(1L);
		image.setLinkImage("abc.com");
		imageList.add(image);
		comment.setImage(image);
		post.setImages(imageList);
		
		
		ImageResDto imageDto = new ImageResDto();
		imageDto.setId(1L);
		imageDto.setLinkImage("abc.com");
		List<ImageResDto> imadeListDto = new ArrayList<ImageResDto>();
		imadeListDto.add(imageDto);
		
		CommentResDto commentDto = new CommentResDto();
		commentDto.setContent("this is a comment");
		commentDto.setImage(imageDto);
		List<CommentResDto> listCommentDto = new ArrayList<CommentResDto>();
		listCommentDto.add(commentDto);
		
		Like like = new Like();
		UserInfo user = new UserInfo();
		user.setId(1L);
		user.setLastName("Dov");
		user.setFirstName("Tuyen");
		like.setUserInfo(user);
		List<Like> likeList = new ArrayList<Like>();
		likeList.add(like);
		post.setLikes(likeList);
		
		UserInforResDto userDto = new UserInforResDto();
		userDto.setId(1L);
		userDto.setLastName("Dov");
		userDto.setFirstName("Tuyen");
		
		commentDto.setUserInfo(userDto);
		
		LikeResDto likeDto = new LikeResDto();
		likeDto.setUserInfo(userDto);
		List<LikeResDto> listLikeDto = new ArrayList<LikeResDto>();
		listLikeDto.add(likeDto);
		
		post.setUserInfo(user);
		
		PostPostResDto resDto = new PostPostResDto();
		resDto.setId(1L);
		resDto.setPrivacy(3);
		resDto.setContent("this is a post");
		
		List<Post> postList = new ArrayList<Post>();
		postList.add(post);
		
		when(postMapper.entityToDto(post)).thenReturn(resDto);
		when(imageMapper.dtoListToEntityList(post.getImages())).thenReturn(imadeListDto);
		when(likeRepository.countByPost(post)).thenReturn(1L);
		when(commentRepository.countByPost(post)).thenReturn(1L);
		when(commentResponseUtils.convert(post.getComments())).thenReturn(listCommentDto);
		when(likeResponseUtils.convert(post.getLikes())).thenReturn(listLikeDto);
		when(userResponseUtils.convert(post.getUserInfo())).thenReturn(userDto);
		
		var result = postResponseUtils.convert(post);
		
		assertEquals(1L, result.getId());
		assertEquals(3, result.getPrivacy());
		assertEquals("this is a post", result.getContent());
		assertEquals(1L, result.getImage().get(0).getId());
		assertEquals("abc.com", result.getImage().get(0).getLinkImage());
		assertEquals(1L, result.getLikeCount());
		assertEquals(1L, result.getLikes().get(0).getUserInfo().getId());
		assertEquals("Dov", result.getLikes().get(0).getUserInfo().getLastName());
		assertEquals("Tuyen", result.getLikes().get(0).getUserInfo().getFirstName());
		assertEquals("this is a comment", result.getComments().get(0).getContent());
		assertEquals(1L, result.getComments().get(0).getUserInfo().getId());
		assertEquals("Dov", result.getComments().get(0).getUserInfo().getLastName());
		assertEquals("Tuyen", result.getComments().get(0).getUserInfo().getFirstName());
		assertEquals(1L, result.getUserInfo().getId());
		assertEquals("Dov", result.getUserInfo().getLastName());
		assertEquals("Tuyen", result.getUserInfo().getFirstName());
		
	}
}
