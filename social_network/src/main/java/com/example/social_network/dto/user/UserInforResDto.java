package com.example.social_network.dto.user;

import java.time.LocalDate;

import com.example.social_network.dto.AbstractDto;
import com.example.social_network.dto.image.ImageResDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserInforResDto extends AbstractDto {

	private Long id;

	private String username;

	private boolean gender;

	@JsonProperty("last_name")
	private String lastName;

	@JsonProperty("first_name")
	private String firstName;

	LocalDate dob;

	private String introyourself;

	@JsonProperty("avatar_image")
	private ImageResDto avatar;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isGender() {
		return gender;
	}

	public void setGender(boolean gender) {
		this.gender = gender;
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

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public String getIntroyourself() {
		return introyourself;
	}

	public void setIntroyourself(String introyourself) {
		this.introyourself = introyourself;
	}

	public ImageResDto getAvatar() {
		return avatar;
	}

	public void setAvatar(ImageResDto avatar) {
		this.avatar = avatar;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}