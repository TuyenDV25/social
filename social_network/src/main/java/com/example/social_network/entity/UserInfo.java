package com.example.social_network.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "userInfo")
public class UserInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String username;

	private String password;

	@Column(columnDefinition = "varchar(255) default 'USER_ROLE'")
	private String roles;

	private String lastName;

	private String firstName;

	@Column
	private LocalDate dob;

	private String introyourself;

	private boolean gender;

	@OneToOne(mappedBy = "userInfo")
	@JsonBackReference
	private PasswordResetToken passwordResetToken;

	@OneToMany(mappedBy = "userInfo")
	private List<Image> avatarImage = new ArrayList<>();

	@OneToMany(mappedBy = "userInfo")
	private List<Post> posts = new ArrayList<>();

	@OneToMany(mappedBy = "userInfo")
	private List<Share> shares = new ArrayList<>();

	@OneToMany(mappedBy = "userInfo")
	private List<Like> likes = new ArrayList<>();
}
