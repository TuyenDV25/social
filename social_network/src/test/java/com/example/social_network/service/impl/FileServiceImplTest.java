package com.example.social_network.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.example.social_network.exception.AppException;
import com.example.social_network.mapper.image.ImageResponseMapper;
import com.example.social_network.repository.ImageRepository;

@ExtendWith(MockitoExtension.class)
public class FileServiceImplTest {

	@Mock
	private Cloudinary cloudinary;

	@Mock
	private ImageRepository imageRepository;

	@Mock
	private ImageResponseMapper imageMapper;

	private FileServiceImpl fileServiceImpl;

	@BeforeEach
	public void setUp() {
		fileServiceImpl = new FileServiceImpl(cloudinary, imageRepository, imageMapper);
	}

	@Test
	void uploadImage_validRequest_success() throws IOException {
		Path path = Paths.get("C:/Users/dovantuyen/Downloads/tutorials.xlsx");
		String name = "tutorials.xlsx";
		String originalFileName = "tutorials.xlsx";
		String contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
		byte[] content = null;
		try {
			content = Files.readAllBytes(path);
		} catch (final IOException e) {
		}
		MultipartFile resultt = new MockMultipartFile(name, originalFileName, contentType, content);

		MultipartFile[] file = { resultt };

		AppException exception = assertThrows(AppException.class, () -> fileServiceImpl.uploadImage(file));

		assertEquals(1003, exception.getErrorCode().getCode());
		assertEquals("file only be JPG or PNG", exception.getMessage());
	}
}
