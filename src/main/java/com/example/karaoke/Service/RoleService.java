package com.example.karaoke.Service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.karaoke.Repository.RoleRepository;
import com.example.karaoke.entity.Role;

@Service
public class RoleService {
	@Autowired
	RoleRepository repo;
	public List<Role> getAll() {
		return repo.findAll();
	}
	public Role save(Role food) {
		return repo.save(food);
	}
	public void remove(int id) {
		repo.deleteById(id);
	}
	public Role getById(int id) {
		Optional<Role> f = repo.findById(id);
		if(f.isPresent()) {
			return f.get();
		}
		return null;
	}
	
}
