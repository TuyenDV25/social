package com.example.social_network.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UtilsTest {

	@Test
	void getLast_validRequest_success() {
		List<Integer> testList = new ArrayList<>();
		testList.add(1);
		testList.add(2);
		testList.add(3);
		Integer lastNumber = Utils.getLast(testList);
		assertEquals(3, lastNumber);
	}


	@Test
	void getLastNull_validRequest_success() {

		Integer lastNumber = Utils.getLast(null);
		assertEquals(null, lastNumber);
	}

	@Test
	void convertStringToLocalDate_validRequest_success() {
		var result =  Utils.StringToDate("abcd");
		assertEquals(null, result);
	}
}
