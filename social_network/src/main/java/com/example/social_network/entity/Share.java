package com.example.social_network.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "share")
@EqualsAndHashCode(callSuper = true)
public class Share extends AbstractEntity {

	@Column
	String content;

	@ManyToOne
	@JoinColumn(name = "user_id")
	UserInfo userInfo;

	@ManyToOne
	@JoinColumn(name = "post_id")
	Post post;

}
