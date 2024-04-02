package com.example.social_network.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.social_network.dto.user.UserInfoPutReqDto;
import com.example.social_network.dto.user.UserInfoPutResDto;
import com.example.social_network.dto.user.UserInforResDto;
import com.example.social_network.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/update")
	public UserInfoPutResDto updateInfo(@Valid @RequestBody UserInfoPutReqDto reqDto) {
		return userService.updateInfo(reqDto);
	}
	
	@GetMapping("/me")
    public UserInforResDto getInformation(){
        return userService.getUserInformation();
    }

}
