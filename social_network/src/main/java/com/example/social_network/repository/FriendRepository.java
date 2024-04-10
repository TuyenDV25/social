package com.example.social_network.repository;

import java.time.OffsetDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.social_network.entity.Friend;
import com.example.social_network.mapper.user.FriendUserInfo;

@Repository
@Transactional
public interface FriendRepository extends JpaRepository<Friend, Long> {

	@Query(value = "select fr.id, fr.user_info_id as userId, fr.created_date as createdDate from friend fr join user_info_list_friend u_fr on fr.id=u_fr.list_friend_id join user_info u on u.id = u_fr.user_info_id and u_fr.user_info_id=?1 order by fr.id desc",
            countQuery = "select count(*) from friend fr join user_info_list_friend u_fr on fr.id=u_fr.list_friend_id join user_info u on u.id = u_fr.user_info_id and u_fr.user_info_id=?1",
            nativeQuery = true)
	Page<FriendUserInfo> findByUserInfo(Long id, Pageable pageable);
	
	@Query(value = "select count (*) from friend fr where fr.user_info_id = ?1 and fr.created_date between ?2 and ?3 ",
			nativeQuery = true)
	Long countNewFriends(Long userInfoId, OffsetDateTime from, OffsetDateTime to);

}
