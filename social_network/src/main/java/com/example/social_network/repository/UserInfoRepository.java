package com.example.social_network.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.social_network.entity.UserInfo;

@Transactional
@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
	Optional<UserInfo> findByUsername(String username);
	
	Page<UserInfo> findAllByFirstNameLikeOrLastNameLike(String firstName, String lastName, Pageable pageable);
}