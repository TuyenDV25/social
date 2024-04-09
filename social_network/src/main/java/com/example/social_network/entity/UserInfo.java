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
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "userInfo")
public class UserInfo extends AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String username;

	private String password;

	@Column(columnDefinition = "varchar(255) default 'USER_ROLE'")
	private String roles;

	private String lastName;

	private String firstName;

	@Column
	private LocalDate dob;

	private String introyourself;

	boolean gender;

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

	@OneToMany
	private List<FriendRequest> listFriendRequest = new ArrayList<>();

	@OneToMany
	private List<Friend> listFriend = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public String getIntroyourself() {
		return introyourself;
	}

	public void setIntroyourself(String introyourself) {
		this.introyourself = introyourself;
	}

	public boolean isGender() {
		return gender;
	}

	public void setGender(boolean gender) {
		this.gender = gender;
	}

	public PasswordResetToken getPasswordResetToken() {
		return passwordResetToken;
	}

	public void setPasswordResetToken(PasswordResetToken passwordResetToken) {
		this.passwordResetToken = passwordResetToken;
	}

	public List<Image> getAvatarImage() {
		return avatarImage;
	}

	public void setAvatarImage(List<Image> avatarImage) {
		this.avatarImage = avatarImage;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public List<Share> getShares() {
		return shares;
	}

	public void setShares(List<Share> shares) {
		this.shares = shares;
	}

	public List<Like> getLikes() {
		return likes;
	}

	public void setLikes(List<Like> likes) {
		this.likes = likes;
	}

	public List<FriendRequest> getListFriendRequest() {
		return listFriendRequest;
	}

	public void setListFriendRequest(List<FriendRequest> listFriendRequest) {
		this.listFriendRequest = listFriendRequest;
	}

	public List<Friend> getListFriend() {
		return listFriend;
	}

	public void setListFriend(List<Friend> listFriend) {
		this.listFriend = listFriend;
	}

}
