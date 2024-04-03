package com.example.social_network.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "comment")
@EqualsAndHashCode(callSuper = true)
public class Comment extends AbstractEntity {

	@Column
	String content;

	@ManyToOne
	@JoinColumn(name = "user_id")
	UserInfo user;

	@ManyToOne
	@JoinColumn(name = "post_id")
	Post post;

	@OneToOne(mappedBy = "comment")
	@JsonBackReference
	Image image;

}
