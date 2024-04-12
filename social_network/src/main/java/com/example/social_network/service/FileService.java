package com.example.social_network.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.social_network.dto.image.ImageResDto;

public interface FileService {

	public List<ImageResDto> uploadImage(MultipartFile[] multipartFile);

}
