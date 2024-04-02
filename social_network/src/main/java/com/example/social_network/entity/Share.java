package com.example.social_network.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@Table(name = "share")
@EqualsAndHashCode(callSuper = true)
public class Share extends AbstractEntity {

	@Column
	private String content;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserInfo user;

	@ManyToOne
	@JoinColumn(name = "post_id")
	private Post post;

}
