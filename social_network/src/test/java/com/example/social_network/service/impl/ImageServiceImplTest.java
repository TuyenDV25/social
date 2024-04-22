package com.example.social_network.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.social_network.entity.Image;
import com.example.social_network.repository.ImageRepository;

@ExtendWith(MockitoExtension.class)
public class ImageServiceImplTest {

	@Mock
	private ImageRepository imageRepository;

	private ImageServiceImpl imageServiceImpl;

	@BeforeEach
	public void setUp() {
		imageServiceImpl = new ImageServiceImpl(imageRepository);
	}

	@Test
	void findOneById_validRequest_success() {
		Image image = new Image();
		image.setId(1L);
		image.setLinkImage("abc.com");
		when(imageRepository.findOneById(1L)).thenReturn(image);
		var result = imageServiceImpl.findOneById(1L);
		assertEquals(1l, result.getId());
		assertEquals("abc.com", result.getLinkImage());
	}
	
	@Test
	void save_validRequest_success() {
		Image image = new Image();
		image.setId(1L);
		image.setLinkImage("abc.com");
		when(imageRepository.save(image)).thenReturn(image);
		var result = imageServiceImpl.save(image);
		assertEquals(1l, result.getId());
		assertEquals("abc.com", result.getLinkImage());
	}
	
	@Test
	void delete_validRequest_success() {
		Image image = new Image();
		image.setId(1L);
		image.setLinkImage("abc.com");
        imageServiceImpl.deleteById(1L);
	}
}
