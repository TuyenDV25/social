package com.example.social_network.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractDto {

	private Date createdDate;

	private Date updateDate;
}
