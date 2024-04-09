package com.example.social_network.dto.friend;

import java.util.Date;

import com.example.social_network.dto.user.UserInforResDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FriendResDto {
	private UserInforResDto userInfo;
	
	private Long id;

    @JsonProperty("created_date")
    private Date createdDate;
}
