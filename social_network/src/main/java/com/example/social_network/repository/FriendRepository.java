package com.example.social_network.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.social_network.entity.Friend;

@Repository
@Transactional
public interface FriendRepository extends JpaRepository<Friend, Long> {

}
