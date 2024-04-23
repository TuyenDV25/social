package com.example.social_network.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OtpGetReqDto {

	@Pattern(regexp = "^[0-9]{6}$", message = "OTP have to be 6 characters of number")
	@NotBlank(message = "OTP is required")
	private String otp;

	@Pattern(regexp = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$", message = "incorrect format")
	@NotNull(message = "userName must be not null!")
	@Schema(example = "username@gmail.net")
	private String userName;
}
