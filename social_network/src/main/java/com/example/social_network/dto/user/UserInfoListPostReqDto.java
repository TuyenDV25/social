package com.example.social_network.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoListPostReqDto {

	private int pageNo;

	private int pageSize;

	private String name;
}
