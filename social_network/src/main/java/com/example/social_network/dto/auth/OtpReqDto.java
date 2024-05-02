package com.example.social_network.dto.auth;

import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OtpReqDto {

	@Pattern(regexp = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$", message = "UserName incorrect format")
	@NotNull(message = "is required")
	@Schema(example = "username@gmail.net")
	private String userName;

	@Length(min = 8, max = 20, message = "must be from 8 to 20 characters!")
	@NotBlank(message = "is required")
	private String password;
}
