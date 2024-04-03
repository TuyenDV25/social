package com.example.social_network.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.social_network.dto.image.ImageResDto;
import com.example.social_network.service.FileService;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UploadFileController {

	@Autowired
	FileService fileService;

	@PostMapping("/upload")
	ResponseEntity<?> upload(@RequestParam(value = "files", required = false) MultipartFile files) {
		ImageResDto restDto = fileService.uploadImage(files);
		return ResponseEntity.ok(restDto);
	}
}
