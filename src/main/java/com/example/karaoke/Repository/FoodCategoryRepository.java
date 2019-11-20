package com.example.karaoke.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.karaoke.entity.FoodCategory;


public interface FoodCategoryRepository extends JpaRepository<FoodCategory, Integer>{
	FoodCategory findByName(String name);
}
