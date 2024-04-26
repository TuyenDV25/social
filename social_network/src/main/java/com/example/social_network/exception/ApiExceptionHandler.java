package com.example.social_network.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.social_network.response.BaseResponse;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ApiExceptionHandler {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, Object> errors = new HashMap<>();
		Map<String, Object> fieldErrors = new HashMap();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorCode = error.getDefaultMessage();
			fieldErrors.put(fieldName, errorCode);
		});
		errors.put("success", false);
		errors.put("messsage", "your input request is not valid");
		errors.put("errors", fieldErrors);
		return errors;
	}

	@ExceptionHandler(value = AppException.class)
	ResponseEntity<BaseResponse<?>> handlingAppException(AppException exception) {
		ErrorCode errorCode = exception.getErrorCode();
		BaseResponse<?> apiResponse = new BaseResponse<>();

		apiResponse.setCode(errorCode.getCode());
		apiResponse.setMessage(errorCode.getMessage());

		return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
	}

	@ExceptionHandler(value = Exception.class)
	ResponseEntity<BaseResponse> handlingRuntimeException(Exception exception) {
		BaseResponse apiResponse = new BaseResponse();

		apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
		apiResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());

		return ResponseEntity.badRequest().body(apiResponse);
	}

	@ExceptionHandler(value = AccessDeniedException.class)
	ResponseEntity<BaseResponse<?>> handlingAccessDeniedException(AccessDeniedException exception) {
		ErrorCode errorCode = ErrorCode.UNAUTHORIZED;

		return ResponseEntity.status(errorCode.getStatusCode())
				.body(BaseResponse.builder().code(errorCode.getCode()).message(errorCode.getMessage()).build());
	}
}
