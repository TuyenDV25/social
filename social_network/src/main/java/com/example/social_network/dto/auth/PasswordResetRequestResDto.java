package com.example.social_network.dto.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * respond reset password request
 */
@Getter
@Setter
@NoArgsConstructor
public class PasswordResetRequestResDto {

	private String linkResetPassword;
}
