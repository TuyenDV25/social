package com.example.social_network.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.social_network.entity.Image;

@Transactional
@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
	Image findOneById(Long id);

	Image save(Image image);

	void deleteById(Long id);
}
