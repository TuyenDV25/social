package com.example.social_network.dto.user;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoPutReqDto {

	@Length(min = 1, max = 20, message = "Họ phải có độ dài từ 1 đến 20")
	@JsonProperty("last_name")
	private String lastName;

	@Length(min = 1, max = 10, message = "Tên phải có độ dài từ 1 đến 10")
	@JsonProperty("first_name")
	private String firstName;

	@DateTimeFormat(pattern = "yyyyMMdd")
	private String birthDay;

	@Length(min = 5, max = 100, message = "Giới thiệu bản thân phải có độ dài từ 5 đến 100")
	private String introyourself;

	private String gender;

	@JsonProperty("hometown")
	private Long idHomeTown;

	@JsonProperty("current_city")
	private Long idCurrentCity;

	@JsonProperty("avatar_image")
	private Long idAvatarImage;
}
