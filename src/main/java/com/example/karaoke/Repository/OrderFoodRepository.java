package com.example.karaoke.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.karaoke.entity.Food;
import com.example.karaoke.entity.OrderFood;
import com.example.karaoke.entity.Room;


public interface OrderFoodRepository extends JpaRepository<OrderFood, Integer>{
	@Query("From OrderFood r Where r.order.id = :id_order and r.food.id = :id_food")
	public List<OrderFood> findOrderFood(@Param("id_order") int id_order, @Param("id_food") int id_food);
}
