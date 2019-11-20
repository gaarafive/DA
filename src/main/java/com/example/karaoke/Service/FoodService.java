package com.example.karaoke.Service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.karaoke.Repository.FoodRepository;
import com.example.karaoke.Repository.OrderFoodRepository;
import com.example.karaoke.entity.Food;
import com.example.karaoke.entity.OrderFood;

@Service
public class FoodService {
	
	@Autowired
	FoodRepository repo;
	public List<Food> getAll() {
		return repo.findAll();
	}
	public Food save(Food food) {
		return repo.save(food);
	}
	public void remove(int id) {
		repo.deleteById(id);
	}
	public Food getById(int id) {
		Optional<Food> f = repo.findById(id);
		if(f.isPresent()) {
			return f.get();
		}
		return null;
	}
	public List<Food> findByNameContaining(String value){
		return repo.findByNameContaining(value);
	}
	public List<Food> findByCategoryId(int id){
		return repo.findByCategoryId(id);
	}
}
