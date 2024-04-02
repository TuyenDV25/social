package com.example.social_network.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.social_network.entity.Post;
import com.example.social_network.entity.Share;

@Repository
@Transactional
public interface ShareRepository extends JpaRepository<Share, Long> {

	Long countByPost(Post post);

}
