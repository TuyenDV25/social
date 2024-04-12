package com.example.social_network.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

import jakarta.validation.Valid;

/**
 * TuyenDV
 */
@RestController
@RequestMapping("api/v1/user")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping(value = "/update", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
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
	public BaseResponse<UserInforResDto> getInformation() {
		UserInforResDto resDto = userService.getUserInformation();
		return BaseResponse.<UserInforResDto>builder().result(resDto).message(CommonConstants.USER_DETAIL_SUCCESS)
				.build();
	}

	@GetMapping("/{userId}")
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
	@PostMapping("/search")
	public BaseResponse<UserInfoListPostResDto> searchUser(@Valid @RequestBody UserInfoListPostReqDto reqDto) {
		Page<UserInforResDto> userInforList = userService.searchUserByName(reqDto);
		return BaseResponse.<UserInfoListPostResDto>builder()
				.result(UserInfoListPostResDto.builder().listUser(userInforList).build())
				.message(CommonConstants.USER_SEARCH_SUCCES).build();
	}
}
