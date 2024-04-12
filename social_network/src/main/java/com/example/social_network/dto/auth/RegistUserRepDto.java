package com.example.social_network.dto.auth;

import org.hibernate.validator.constraints.Length;

import com.example.social_network.enumdef.RoleType;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistUserRepDto {

	private int id;

	@Pattern(regexp = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$", message = "incorrect format")
	@NotNull(message = "Tài khoản nhập null!")
	private String username;

	@Length(min = 3, max = 20, message = "must be from 3 to 20 characters!")
	private String lastName;

	@Length(min = 3, max = 20, message = "must be from 3 to 20 characters!")
	private String firstName;

	@Length(min = 6, max = 20, message = "must be from 6 to 20 characters!")
	private String password;

	@Pattern(regexp = "^[0-1]{1}[0-9]{0,2}$", message = "have to be number")
	private String age;

	private String roles = RoleType.USER.name();

}
