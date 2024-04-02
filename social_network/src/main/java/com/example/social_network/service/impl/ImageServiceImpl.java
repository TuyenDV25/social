package com.example.social_network.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.social_network.entity.Image;
import com.example.social_network.repository.ImageRepository;
import com.example.social_network.service.ImageService;

@Service
public class ImageServiceImpl implements ImageService {

	@Autowired
	ImageRepository imageRepository;

	@Override
	public Image findOneById(Long id) {

		return imageRepository.findOneById(id);
	}

	@Override
	public Image save(Image image) {

		return imageRepository.save(image);
	}

	@Override
	public void deleteById(Long id) {
		imageRepository.deleteById(id);
	}

}
