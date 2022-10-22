package com.flightman.flightmanapi.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.flightman.flightmanapi.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	User findByUserId(UUID userId);

	User findByEmail(String email);

	// findAll

	@Modifying
	@Query(value = "DELETE FROM User WHERE user_id = :id")
	int deleteById(@Param("id") int id);

	@Modifying
	@Query(value = "DELETE FROM User WHERE email = :email")
	int deleteByEmail(@Param("email") String email);

	// deleteAll

	// Create and Update: userRepository.save(user);
}
