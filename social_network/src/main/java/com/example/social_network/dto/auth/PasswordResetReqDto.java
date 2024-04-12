package com.example.social_network.dto.auth;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetReqDto {

	@Pattern(regexp = "^[0-9]{6}$", message = "OTP have to be 6 characters of number")
	private String token;

	@Length(min = 6, max = 20, message = "must be from 6 to 20 characters!")
	private String password;
}
