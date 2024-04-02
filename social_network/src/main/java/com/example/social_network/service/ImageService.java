package com.example.social_network.service;

import com.example.social_network.entity.Image;

public interface ImageService {

	Image findOneById(Long id);

	Image save(Image image);

	void deleteById(Long id);
	
}
