package com.example.social_network.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * respond reset password request
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetRequestResDto {

	private String linkResetPassword;
}
