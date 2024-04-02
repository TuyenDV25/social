package com.example.social_network.service;

import org.springframework.web.multipart.MultipartFile;

import com.example.social_network.dto.image.ImageResDto;

public interface FileService {
	
	public ImageResDto uploadImage(MultipartFile multipartFile);

}
