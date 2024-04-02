package com.example.social_network.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@Table(name = "image")
@EqualsAndHashCode(callSuper = true)
public class Image extends AbstractEntity {

	private String linkImage;

	@ManyToOne
	@JoinColumn(name = "userInfor_id")
	private List<UserInfo> userAvatar = new ArrayList<>();

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "post_id", referencedColumnName = "id")
	@JsonManagedReference
	private Post post;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "comment_id", referencedColumnName = "id")
	@JsonManagedReference
	private Comment comment;

}
