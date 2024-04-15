package com.example.social_network.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.social_network.dto.user.UserInfoListPostReqDto;
import com.example.social_network.dto.user.UserInfoListPostResDto;
import com.example.social_network.dto.user.UserInfoPutReqDto;
import com.example.social_network.dto.user.UserInforResDto;
import com.example.social_network.response.BaseResponse;
import com.example.social_network.service.UserService;
import com.example.social_network.utils.CommonConstants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

/**
 * TuyenDV
 */
@RestController
@RequestMapping("api/v1/user")
public class UserController {

	@Autowired
	private UserService userService;

	@PutMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	@Operation(summary = "API update info of user")
	@ApiResponse(responseCode = "200", description = "update info of user successfully")
	public BaseResponse<UserInforResDto> updateInfo(@RequestPart @Valid UserInfoPutReqDto reqDto,
			@RequestPart(required = false) MultipartFile[] multipartFile) {

		UserInforResDto resDto = userService.updateInfo(reqDto, multipartFile);
		return BaseResponse.<UserInforResDto>builder().result(resDto).message(CommonConstants.USER_UPDATE_SUCCESS)
				.build();
	}

	/**
	 * get information of user login
	 * 
	 * @return {@link UserInforResDto}
	 */
	@GetMapping("/me")
	@Operation(summary = "API get profile of user")
	@ApiResponse(responseCode = "200", description = "get profile successfully")
	public BaseResponse<UserInforResDto> getInformation() {
		UserInforResDto resDto = userService.getUserInformation();
		return BaseResponse.<UserInforResDto>builder().result(resDto).message(CommonConstants.USER_DETAIL_SUCCESS)
				.build();
	}

	@GetMapping("/{userId}")
	@Operation(summary = "API get profile user by Id")
	@ApiResponse(responseCode = "200", description = "get profile successfully")
	public BaseResponse<UserInforResDto> getInformationById(@PathVariable("userId") Long id) {
		UserInforResDto resDto = userService.findDetailUser(id);
		return BaseResponse.<UserInforResDto>builder().result(resDto).message(CommonConstants.USER_DETAIL_SUCCESS)
				.build();
	}

	/**
	 * get list information of user
	 * 
	 * @param reqDto {@link UserInfoListPostReqDto}
	 * @return {@link UserInfoListPostResDto} list of user by name and size and
	 *         index
	 */
	@GetMapping("/search")
	@Operation(summary = "API search user by name")
	@ApiResponse(responseCode = "200", description = "search successfully")
	public BaseResponse<UserInfoListPostResDto> searchUser(@RequestParam Integer pageNumber,
			@RequestParam String searchContent) {
		Page<UserInforResDto> userInforList = userService.searchUserByName(pageNumber, searchContent);
		return BaseResponse.<UserInfoListPostResDto>builder()
				.result(UserInfoListPostResDto.builder().listUser(userInforList).build())
				.message(CommonConstants.USER_SEARCH_SUCCES).build();
	}
}
