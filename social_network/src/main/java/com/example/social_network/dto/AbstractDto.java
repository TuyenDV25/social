package com.example.social_network.dto;

import java.util.Date;

import lombok.Data;

@Data
public abstract class AbstractDto {

	private Date createdDate;

	private Date updateDate;
}
