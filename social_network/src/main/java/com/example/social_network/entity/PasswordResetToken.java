package com.example.social_network.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@Table(name = "password_reset_tokens")
public class PasswordResetToken implements Serializable {
	/**
	* 
	*/
	static final long serialVersionUID = -8308103083764632795L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	String token;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "userInfo_id", referencedColumnName = "id")
	@JsonManagedReference
	UserInfo userInfo;

}
