package com.example.social_network.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.social_network.entity.Like;
import com.example.social_network.entity.Post;
import com.example.social_network.entity.UserInfo;

@Repository
@Transactional
public interface LikeRepository extends JpaRepository<Like, Long> {
	Long countByPostAndStatus(Post post, Boolean status);

	Like findOneByPostAndUserInfo(Post post, UserInfo userInfo);
}
