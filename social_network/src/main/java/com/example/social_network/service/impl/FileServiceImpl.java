package com.example.social_network.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.example.social_network.dto.image.ImageResDto;
import com.example.social_network.entity.Image;
import com.example.social_network.exception.AppException;
import com.example.social_network.exception.ErrorCode;
import com.example.social_network.mapper.image.ImageResponseMapper;
import com.example.social_network.repository.ImageRepository;
import com.example.social_network.service.FileService;

@Service
public class FileServiceImpl implements FileService {

	@Autowired
	Cloudinary cloudinary;

	@Autowired
	ImageRepository imageRepository;

	@Autowired
	ImageResponseMapper imageMapper;

	@Override
	public List<ImageResDto> uploadImage(MultipartFile[] files) {
		if (files != null && files.length > 0) {
			List<ImageResDto> listResDto = new ArrayList<>();
			for (MultipartFile file : files) {
				String formatFile = FilenameUtils.getExtension(file.getOriginalFilename()).toLowerCase();
				if (!formatFile.equals("jpg") && !formatFile.equals("png") && !formatFile.equals("jpeg"))
					throw new AppException(ErrorCode.EXTENSION_INVALID);
				if (file.getSize() > 5242880)
					throw new AppException(ErrorCode.FILE_SIZE);
				try {
					String link = cloudinary.uploader()
							.upload(file.getBytes(), Map.of("public_id", UUID.randomUUID().toString())).get("url")
							.toString();
					Image image = new Image();
					image.setLinkImage(link);
					ImageResDto restDto = imageMapper.entityToDto(imageRepository.save(image));
					listResDto.add(restDto);
				} catch (IOException e) {
					throw new AppException(ErrorCode.FILE_UPLOAD);
				}
			}
			return listResDto;
		} else {
			throw new AppException(ErrorCode.FILE_UPLOAD);
		}
	}
}
