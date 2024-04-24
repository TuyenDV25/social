package com.example.social_network.dto.user;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonProperty;

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
	private String birthDay;

	@Length(min = 5, max = 100, message = "must be from 5 to 100 characters")
	private String introyourself;

	private String gender;

	@JsonProperty("hometown")
	private long idHomeTown;

	@JsonProperty("current_city")
	private long idCurrentCity;
}
