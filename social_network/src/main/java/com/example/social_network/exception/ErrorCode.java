package com.example.social_network.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum ErrorCode {

	UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
	INVALID_KEY(1001, "Uncategorized error", HttpStatus.UNAUTHORIZED),
	USER_EXISTED(1002, "User existed", HttpStatus.BAD_REQUEST),
	USERNAME_INVALID(1003, "Username must be at least 3 characters", HttpStatus.BAD_REQUEST),
	INVALID_PASSWORD(1003, "Password must be at least 8 characters", HttpStatus.BAD_REQUEST),
	USER_NOT_EXISTED(1002, "User not existed", HttpStatus.NOT_FOUND),
	SIGNIN_ERROR(1008, "User not existed", HttpStatus.UNAUTHORIZED),
	UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
	UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),
	TOKEN_RESET_PSW_WRONG(1008, "have error reset password", HttpStatus.UNAUTHORIZED),
	OTP_INVALID(1003, "otp or userName is invalid", HttpStatus.UNAUTHORIZED),
	EXTENSION_INVALID(1003, "file only be JPG or PNG", HttpStatus.BAD_REQUEST),
	FILE_SIZE(1005, "your file over 5mb", HttpStatus.BAD_REQUEST),
	FILE_UPLOAD(1012, "upload image error", HttpStatus.BAD_REQUEST),
	POST_NOTEXISTED(1002, "post not existed", HttpStatus.NOT_FOUND),
	POST_NOT_RIGHT(1002, "you can not fix or delete other's post", HttpStatus.BAD_REQUEST),
	COMMENT_NOT_RIGHT(1002, "you can not fix or delete other's comment", HttpStatus.BAD_REQUEST),
	COMMENT_NOTEXISTED(1002, "comment not existed", HttpStatus.NOT_FOUND),
	POST_UPLOAD_WRONG(1004, "post must have at least content or file", HttpStatus.BAD_REQUEST),
	ADD_FRIEND_YOUSELF(1004, "You can not add friend youself", HttpStatus.BAD_REQUEST),
	ADD_REQUEST_TO_FRIEND(1004, "can not add friend with your friend", HttpStatus.BAD_REQUEST),
	REMOVE_REQUEST_TO_FRIEND(1004, "can not remove request when have been friend", HttpStatus.BAD_REQUEST),
	ADD_REQUEST_AGAIN_TO_FRIEND(1004, "you have send request to your friend", HttpStatus.BAD_REQUEST),
	NOT_EXIST_REQUEST(1004, "no request existed", HttpStatus.BAD_REQUEST),
	NOT_EXIST_FRIEND_REQUEST(1004, "you have no request friend", HttpStatus.BAD_REQUEST),
	ACCEPT_YOUR_REQUEST(1004, "you can not accept your request", HttpStatus.BAD_REQUEST),
	NOT_FRIEND(1004, "not friend to remove", HttpStatus.BAD_REQUEST),
	REMOVE_FRIEND_YOURSELF(1004, "can not remove friend yourself", HttpStatus.BAD_REQUEST),
	DELETE_IMAGE_POST_WRONG(1004, "delete image not in post", HttpStatus.BAD_REQUEST),
	DELETE_IMAGE_COMMENT_WRONG(1004, "delete image of comment is wrong", HttpStatus.BAD_REQUEST),
	REMOVE_FRIENDREQUEST_YOURSELF(1004, "can not remove friend request yourself", HttpStatus.BAD_REQUEST),
	ERROR_IMPORT_DATA_EXCEL(1001,"fail to import data to Excel file",HttpStatus.BAD_REQUEST),
	COMMENT_UPLOAD_WRONG(1004, "comment must have at least content or file", HttpStatus.BAD_REQUEST),;

	ErrorCode(int code, String message, HttpStatusCode statusCode) {
		this.code = code;
		this.message = message;
		this.statusCode = statusCode;
	}

	private int code;
	private String message;
	private HttpStatusCode statusCode;
}
