package com.example.social_network.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.social_network.entity.Comment;
import com.example.social_network.entity.Post;
import com.example.social_network.entity.UserInfo;

@Repository
@Transactional
public interface CommentRepository extends JpaRepository<Comment, Long> {
	Long countByPost(Post post);

	Comment findOneById(Long id);

	Comment findByUserAndId(UserInfo user, Long Id);
	
	Page<Comment> findByPost(Post post, Pageable pageable);
}
