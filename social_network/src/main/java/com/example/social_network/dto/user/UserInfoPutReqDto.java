package com.example.social_network.dto.user;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

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
public class UserInfoPutReqDto {

	@Length(min = 1, max = 20, message = "Họ phải có độ dài từ 1 đến 20")
	@JsonProperty("last_name")
	String lastName;

	@Length(min = 1, max = 10, message = "Tên phải có độ dài từ 1 đến 10")
	@JsonProperty("first_name")
	String firstName;

	@DateTimeFormat(pattern = "yyyyMMdd")
	String birthDay;

	@Length(min = 5, max = 100, message = "Giới thiệu bản thân phải có độ dài từ 5 đến 100")
	String introyourself;

	String gender;

	@JsonProperty("hometown")
	Long idHomeTown;

	@JsonProperty("current_city")
	Long idCurrentCity;

	@JsonProperty("avatar_image")
	Long idAvatarImage;
}
