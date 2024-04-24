package com.example.social_network.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;

@ExtendWith(MockitoExtension.class)
public class ApiExceptionHandlerTest {
	private final ApiExceptionHandler handler = new ApiExceptionHandler();

	@Test
	void methodArgumentNotValidException_returnErrorValidate() {
		var ex = new AppException(ErrorCode.ACCEPT_YOUR_REQUEST);

		var response = handler.handlingAppException(ex);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals(1004, response.getBody().getCode());
		assertEquals("you can not accept your request", response.getBody().getMessage());
	}
	
	@Test
	void handlingRuntimeException_returnErrorValidate() {
		var ex = new RuntimeException("loi roi");

		var response = handler.handlingRuntimeException(ex);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals(9999, response.getBody().getCode());
		assertEquals("Uncategorized error", response.getBody().getMessage());
	}
	
	@Test
	void handlingAccessDeniedException_returnErrorValidate() {
		var ex = new AccessDeniedException("loi roi");

		var response = handler.handlingAccessDeniedException(ex);

		assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
		assertEquals(1007, response.getBody().getCode());
		assertEquals("You do not have permission", response.getBody().getMessage());
	}
}
