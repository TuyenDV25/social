package com.example.social_network.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordResetReqDto {

	@NotBlank(message = "is required")
	private String token;

	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$", message = "Minimum eight characters, Maximum 20 characters, at least one uppercase letter, one lowercase letter, one number and one special character")
	@NotBlank(message = "is required")
	private String password;
}
