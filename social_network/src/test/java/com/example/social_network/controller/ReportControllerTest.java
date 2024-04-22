package com.example.social_network.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.social_network.service.ReportService;

@ExtendWith(MockitoExtension.class)
public class ReportControllerTest {

	@Mock
	private ReportService reportService;

	private ReportController reportController;

	@BeforeEach
	void initData() {
		reportController = new ReportController(reportService);
	}

	@Test
	void getFile_validRequest_success() {
		when(reportService.load()).thenReturn(mock(ByteArrayInputStream.class));

		var result = reportController.getFile();

		assertEquals(false, result.getBody().isFile());
	}

}
