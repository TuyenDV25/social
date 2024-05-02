package com.example.social_network.dto.user;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoPutReqDto {

	@Length(min = 3, max = 20, message = "must be from 3 to 20 characters!")
	@JsonProperty("last_name")
	private String lastName;

	@Length(min = 3, max = 20, message = "must be from 3 to 20 characters!")
	@JsonProperty("first_name")
	private String firstName;

	@DateTimeFormat(pattern = "yyyyMMdd")
	@Pattern(regexp = "^(?:(?:19|20)[0-9]{2})(?:(?:(?:0[1-9]|1[0-2])(?:0[1-9]|1\\d|2[0-8]))|(?:0[13-9]|1[0-2])(29|30)|(?:0[13578]|1[02])31)$", message = "incorrect format")
	private String birthDay;

	@Length(min = 5, max = 100, message = "must be from 5 to 100 characters")
	private String introyourself;

	@Pattern(regexp = "^[0-1]{1}$", message = "must be 0 or 1")
	private String gender;

	@JsonProperty("hometown")
	private long idHomeTown;

	@JsonProperty("current_city")
	private long idCurrentCity;
}
