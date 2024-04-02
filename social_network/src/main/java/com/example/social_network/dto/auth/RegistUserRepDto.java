package com.example.social_network.dto.auth;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}
	
}
