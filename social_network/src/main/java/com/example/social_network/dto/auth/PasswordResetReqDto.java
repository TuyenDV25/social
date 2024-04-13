package com.example.social_network.dto.auth;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetReqDto {

	@NotBlank(message = "token must be not null!")
	private String token;

	@Length(min = 6, max = 20, message = "must be from 6 to 20 characters!")
	@NotBlank(message = "password must be not null!")
	private String password;
}
