package com.example.social_network.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.social_network.entity.PasswordResetToken;

@Transactional
@Repository
public interface PasswordResetTokenReponsitory extends CrudRepository<PasswordResetToken, Long> {

	PasswordResetToken findByToken(String token);

}
