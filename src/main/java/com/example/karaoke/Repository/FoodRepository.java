package com.example.karaoke.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.karaoke.entity.Food;


public interface FoodRepository extends JpaRepository<Food, Integer>{
	public List<Food> findByNameContaining(String term);
	@Query("From Food r Where r.foodCategory.id = :id")
	public List<Food> findByCategoryId(int id);
}
