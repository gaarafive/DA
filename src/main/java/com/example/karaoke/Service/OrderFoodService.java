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
public class OrderFoodService {
	@Autowired
	OrderFoodRepository repo;
	
	public List<OrderFood> getAll() {
		return repo.findAll();
	}
	public OrderFood save(OrderFood orderFood) {
		return repo.save(orderFood);
	}
	public void remove(int id) {
		repo.deleteById(id);
	}
	public OrderFood getById(int id) {
		Optional<OrderFood> f = repo.findById(id);
		if(f.isPresent()) {
			return f.get();
		}
		return null;
	}
	
	public List<OrderFood> findOrderFood(int in_order, int id_food) {
		return repo.findOrderFood(in_order, id_food);
	}
}
