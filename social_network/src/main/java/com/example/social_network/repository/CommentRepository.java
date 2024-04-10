package com.example.social_network.repository;

import java.time.OffsetDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

	@Query(value = "select count(*) from comment c where c.user_id = ?1 and c.created_date between ?2 and ?3", nativeQuery = true)
	Long countComment(Long userInfoId, OffsetDateTime from, OffsetDateTime to);
}
