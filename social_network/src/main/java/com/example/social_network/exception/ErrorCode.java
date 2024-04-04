package com.example.social_network.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum ErrorCode {

	UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
	INVALID_KEY(1001, "Uncategorized error", HttpStatus.BAD_REQUEST),
	USER_EXISTED(1002, "User existed", HttpStatus.BAD_REQUEST),
	USERNAME_INVALID(1003, "Username must be at least 3 characters", HttpStatus.BAD_REQUEST),
	INVALID_PASSWORD(1004, "Password must be at least 8 characters", HttpStatus.BAD_REQUEST),
	USER_NOT_EXISTED(1005, "User not existed", HttpStatus.NOT_FOUND),
	UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
	UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),
	TOKEN_EXPIRED(1008,"time out, you need to login again",HttpStatus.UNAUTHORIZED),
	OTP_INVALID(1009,"otp is invalid",HttpStatus.BAD_REQUEST),
	EXTENSION_INVALID(1010,"file only be JPG or PNG",HttpStatus.BAD_REQUEST),
	FILE_SIZE(1011,"your file over 5mb",HttpStatus.BAD_REQUEST),
	FILE_UPLOAD(1012,"upload image error",HttpStatus.BAD_REQUEST),
	POST_NOTEXISTED(1002, "post not existed", HttpStatus.BAD_REQUEST),
	COMMENT_NOTEXISTED(1002, "comment not existed", HttpStatus.BAD_REQUEST),
	POST_UPLOAD_WRONG(1013, "post must have at least content or file", HttpStatus.BAD_REQUEST),
	COMMENT_UPLOAD_WRONG(1013, "comment must have at least content or file", HttpStatus.BAD_REQUEST),
	;

	ErrorCode(int code, String message, HttpStatusCode statusCode) {
		this.code = code;
		this.message = message;
		this.statusCode = statusCode;
	}

	int code;
	String message;
	HttpStatusCode statusCode;
}
