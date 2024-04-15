package com.example.social_network.repository;

import java.util.Date;

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
public interface PostRepository extends JpaRepository<Post, Long> {

	Post findByUserInfoAndId(UserInfo userInfo, Long id);

	Post findOneById(Long id);
	
	Post findByComments(Comment comment);

	Page<Post> findByUserInfo(UserInfo userInfo, Pageable pageable);
	
	Page<Post> findByContentContains(String content, Pageable pageable);
	
	@Query(value = "select p.* from friend fr join user_info_list_friend u_fr on fr.id=u_fr.list_friend_id join post p on p.user_info_id = u_fr.user_info_id and fr.user_info_id=?1 order by p.created_date desc ",
            countQuery = "select count(*) from friend fr join user_info_list_friend u_fr on fr.id=u_fr.list_friend_id join post p on p.user_info_id = u_fr.user_info_id and fr.user_info_id=?1",
            nativeQuery = true)
	Page<Post> findByUserInfoId(Long userInfoId, Pageable pageable);
	
	@Query(value = "select count(*) from post p where p.user_info_id = ?1 and p.created_date between ?2 and ?3",
			nativeQuery = true)
	long countPosts(Long userInfoId, Date from, Date to);

}
