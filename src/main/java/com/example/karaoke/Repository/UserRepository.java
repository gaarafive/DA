package com.example.karaoke.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.karaoke.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	User findByUsername(String username);
}
