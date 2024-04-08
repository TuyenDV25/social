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
public class RegistUserRepDto {

	private int id;

	@Pattern(regexp = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$", message = "UserName không đúng định dạng")
	@NotNull(message = "Tài khoản nhập null!")
	private String username;

	@Length(min = 3, max = 20, message = "Họ phải từ 3 đến 20 kí tự!")
	private String lastName;

	@Length(min = 3, max = 20, message = "Tên phải từ 3 đến 20 kí tự!")
	private String firstName;

	@Length(min = 6, max = 20, message = "Mật khẩu phải từ 6 đến 20 kí tự!")
	private String password;

	@Pattern(regexp = "^[0-1]{1}[0-9]{0,2}$", message = "Tuổi nhập không chính xác")
	private String age;

	private String roles;

}
