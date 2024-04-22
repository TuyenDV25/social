package com.example.social_network.helper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ExcelHelperTest {

	@BeforeEach
	public void setUp() {

	}

	@Test
	void reportToExcel_validRequest_success() {
		ExcelHelper.reportToExcel(1L, 2L, 3L, 4L);
	}

}
