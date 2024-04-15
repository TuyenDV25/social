package com.example.social_network.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.social_network.dto.post.PostListResDto;
import com.example.social_network.dto.post.PostPostResDto;
import com.example.social_network.response.BaseResponse;
import com.example.social_network.service.TimeLikeService;
import com.example.social_network.utils.CommonConstants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("api/v1/timeline")
public class TimelineController {
	
	@Autowired
	private TimeLikeService timeLikeService;
	
	@GetMapping("/me")
	@Operation(summary = "API timeline")
	@ApiResponse(responseCode = "200", description = "get timeline successfully")
	@ApiResponse(responseCode = "400", description = "get timeline error")
	BaseResponse<PostListResDto> getTimeLine(@RequestParam Integer pageNumber,
			@RequestParam Integer pageSize){
		Pageable paging = PageRequest.of(pageNumber, pageSize);
		Page<PostPostResDto> result = timeLikeService.getTimeLinePost(paging);
		return BaseResponse.<PostListResDto>builder().result(PostListResDto.builder().listPost(result).build())
				.message(CommonConstants.TIME_LINE_SUCCESS).build();

	}
}
