package com.example.social_network.dto.user;

import java.time.LocalDate;

import com.example.social_network.dto.image.ImageResDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserInforResDto {
	String username;

	boolean gender;

	@JsonProperty("last_name")
	String lastName;

	@JsonProperty("first_name")
	String firstName;

	LocalDate dob;

	String bio;

	@JsonProperty("avatar_image")
	ImageResDto avatar;
}
