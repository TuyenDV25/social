package com.example.social_network.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.social_network.entity.Comment;
import com.example.social_network.entity.Post;

@Repository
@Transactional
public interface CommentRepository extends JpaRepository<Comment, Long> {
	Long countByPost(Post post);

}
