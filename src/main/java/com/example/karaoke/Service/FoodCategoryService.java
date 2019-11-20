package com.example.karaoke.Service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.karaoke.Repository.FoodCategoryRepository;
import com.example.karaoke.entity.FoodCategory;

@Service
public class FoodCategoryService {
	@Autowired
	FoodCategoryRepository repo;
	public List<FoodCategory> getAll() {
		return repo.findAll();
	}
	public FoodCategory save(FoodCategory food) {
		return repo.save(food);
	}
	public void remove(int id) {
		repo.deleteById(id);
	}
	public FoodCategory getById(int id) {
		Optional<FoodCategory> f = repo.findById(id);
		if(f.isPresent()) {
			return f.get();
		}
		return null;
	}
	public FoodCategory findByName(String food) {
		return repo.findByName(food);
	}
}
