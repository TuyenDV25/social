package com.example.social_network.dto.user;

import java.time.LocalDate;

import com.example.social_network.dto.image.ImageResDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class UserInforResDto {
	private String username;

	private boolean gender;

	@JsonProperty("last_name")
	private String lastName;

	@JsonProperty("first_name")
	private String firstName;

	private LocalDate dob;

	private String bio;

	@JsonProperty("avatar_image")
	private ImageResDto avatar;
}
