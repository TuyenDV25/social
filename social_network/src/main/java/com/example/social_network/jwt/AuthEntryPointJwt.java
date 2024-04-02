package com.example.social_network.jwt;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.example.social_network.exception.ErrorCode;
import com.example.social_network.response.BaseResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

	private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		logger.error("Unauthorized error: {}", authException.getMessage());

		ErrorCode errorCode = ErrorCode.UNAUTHENTICATED;

		response.setStatus(errorCode.getStatusCode().value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);

		BaseResponse<?> apiResponse = BaseResponse.builder().code(errorCode.getCode()).message(errorCode.getMessage())
				.build();

		ObjectMapper objectMapper = new ObjectMapper();

		response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
		response.flushBuffer();
	}

}
