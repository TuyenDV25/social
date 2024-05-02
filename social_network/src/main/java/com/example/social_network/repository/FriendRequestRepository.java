package com.example.social_network.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.social_network.entity.FriendRequest;

@Repository
@Transactional
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

	@Query(value = "select fr.* from friend_request fr join user_info_list_friend_request ufr on fr.id = ufr.list_friend_request_id join user_info u on u.id = ufr.user_info_id and ufr.user_info_id=?1 order by fr.id desc", countQuery = "select count(*) from friend_request fr join user_info_list_friend_request ufr on fr.id = ufr.list_friend_request_id and ufr.user_info_id=?1", nativeQuery = true)
	Page<FriendRequest> findByUserInfo(Long id, Pageable pageable);

}
