package com.example.social_network.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.example.social_network.dto.post.PostPostResDto;
import com.example.social_network.service.TimeLikeService;

@ExtendWith(MockitoExtension.class)
public class TimelineControllerTest {
	
	@Mock
	private TimeLikeService timeLikeService;
	
	private TimelineController timelineController;
	
	@BeforeEach
	void initData() {
		timelineController = new TimelineController(timeLikeService);
	}
	
	@Test
	void getTimeLine_validRequest_success() {
		var pageable = PageRequest.of(0, 10);
		List<PostPostResDto> listUser = new ArrayList<>();

		Page<PostPostResDto> userInfo = new PageImpl<>(listUser);
		
		when(timeLikeService.getTimeLinePost(pageable)).thenReturn(userInfo);
		
		var result = timelineController.getTimeLine(0,10);
		
		assertEquals(1000, result.getCode());
		assertEquals("get timeline successfully", result.getMessage());
	}

}
