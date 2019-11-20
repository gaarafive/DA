package com.example.karaoke.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.karaoke.entity.Role;


public interface RoleRepository extends JpaRepository<Role, Integer>{
	Role findByName(String name);
}
