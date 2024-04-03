package com.example.social_network.dto.auth;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegistUserRepDto {

	int id;

	@Pattern(regexp = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$", message = "UserName không đúng định dạng")
	@NotNull(message = "Tài khoản nhập null!")
	String username;

	@Length(min = 3, max = 20, message = "Họ phải từ 3 đến 20 kí tự!")
	String lastName;

	@Length(min = 3, max = 20, message = "Tên phải từ 3 đến 20 kí tự!")
	String firstName;

	@Length(min = 6, max = 20, message = "Mật khẩu phải từ 6 đến 20 kí tự!")
	String password;

	@Pattern(regexp = "^[0-1]{1}[0-9]{0,2}$", message = "Tuổi nhập không chính xác")
	String age;

	String roles;

}
