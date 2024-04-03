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
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "userInfo")
public class UserInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	String username;

	String password;

	@Column(columnDefinition = "varchar(255) default 'USER_ROLE'")
	String roles;

	String lastName;

	String firstName;

	@Column
	LocalDate dob;

	String introyourself;

	boolean gender;

	@OneToOne(mappedBy = "userInfo")
	@JsonBackReference
	PasswordResetToken passwordResetToken;

	@OneToMany(mappedBy = "userInfo")
	List<Image> avatarImage = new ArrayList<>();

	@OneToMany(mappedBy = "userInfo")
	List<Post> posts = new ArrayList<>();

	@OneToMany(mappedBy = "userInfo")
	List<Share> shares = new ArrayList<>();

	@OneToMany(mappedBy = "userInfo")
	List<Like> likes = new ArrayList<>();
}
