package com.example.social_network.dto.auth;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtpReqDto {
	
	@Pattern(regexp = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$", message = "UserName incorrect format")
	@NotNull(message = "Tài khoản nhập null!")
	private String userName;

	@Length(min = 6, max = 20, message = "must be from 6 to 20 characters!")
	private String password;
}
