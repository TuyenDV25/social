package com.example.social_network.entity;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import com.example.social_network.dto.auth.OtpGetReqDto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@MappedSuperclass
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@Column(updatable = false)
	@CreationTimestamp
	Date createdDate;
	
	@Column(updatable = true)
	@CreationTimestamp
	Date updateDate;
}
