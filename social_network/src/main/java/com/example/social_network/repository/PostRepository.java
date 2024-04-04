package com.example.social_network.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.social_network.entity.Post;
import com.example.social_network.entity.UserInfo;

@Repository
@Transactional
public interface PostRepository extends JpaRepository<Post, Long> {

	Long findByUserInfoAndId(UserInfo userInfo, Long id);

	Post findOneById(Long id);

	Page<Post> findAllByUserInfoOrderbyIdDesc(UserInfo userInfo, Pageable pageable);
	
	Page<Post> findAllByContentContains(String content, Pageable pageable);

}
