package com.example.social_network.repository;

import java.time.OffsetDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.social_network.entity.Comment;
import com.example.social_network.entity.Like;
import com.example.social_network.entity.Post;
import com.example.social_network.entity.UserInfo;

@Repository
@Transactional
public interface LikeRepository extends JpaRepository<Like, Long> {
	Long countByPost(Post post);

	Like findOneByPostAndUserInfo(Post post, UserInfo userInfo);

	Like findOneByCommentAndUserInfo(Comment comment, UserInfo userInfo);

	@Query(value = "select count(*) from likes l where l.user_id = ?1 and l.created_date between ?2 and ?3", nativeQuery = true)
	Long countLike(Long userInfoId, OffsetDateTime from, OffsetDateTime to);
}
