package com.example.social_network.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "post")
@EqualsAndHashCode(callSuper = true)
public class Post extends AbstractEntity {

	@ManyToOne
	@JoinColumn(name = "userInfo_id", updatable = false)
	UserInfo userInfo;

	@Column
	String content;

	@Column
	Integer privacy;

	@OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
	List<Comment> comments = new ArrayList<>();

	@OneToOne(mappedBy = "post")
	@JsonBackReference
	Image image;

	@OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
	List<Like> likes = new ArrayList<>();

	@OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
	List<Share> shares = new ArrayList<>();

}
