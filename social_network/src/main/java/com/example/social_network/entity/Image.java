package com.example.social_network.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
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
@Table(name = "image")
@EqualsAndHashCode(callSuper = true)
public class Image extends AbstractEntity {

	String linkImage;

	@ManyToOne
	@JoinColumn(name = "userInfo_id")
	UserInfo userInfo;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "post_id", referencedColumnName = "id")
	@JsonManagedReference
	Post post;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "comment_id", referencedColumnName = "id")
	@JsonManagedReference
	Comment comment;

}
