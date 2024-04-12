package com.example.social_network.dto.auth;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtpGetReqDto {

	@Pattern(regexp = "^[0-9]{6}$", message = "OTP have to be 6 characters of number")
	private int otp;

	@Pattern(regexp = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$", message = "incorrect format")
	private String userName;
}
