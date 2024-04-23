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
public class RegistUserRepDto {

	@Pattern(regexp = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$", message = "incorrect format")
	@NotNull(message = "is required")
	@Schema(example = "username@gmail.net")
	private String username;

	@Length(min = 6, max = 20, message = "must be from 6 to 20 characters!")
	@NotBlank(message = "is required")
	private String password;
}
