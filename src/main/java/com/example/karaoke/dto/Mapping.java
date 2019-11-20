package com.example.karaoke.dto;

import com.example.karaoke.Service.MyUserDetailsService;
import com.example.karaoke.entity.Bill;
import com.example.karaoke.entity.Food;
import com.example.karaoke.entity.Order;
import com.example.karaoke.entity.OrderFood;
import com.example.karaoke.entity.Role;
import com.example.karaoke.entity.Room;
import com.example.karaoke.entity.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Mapping {
	@Autowired
	MyUserDetailsService uService;
	public static void userMap(UserDTO r, User l) {
		r.setId(l.getId());
		r.setName(l.getName());
		r.setUsername(l.getUsername());
		r.setRole(l.getRole().getName());
		r.setTelephonenumber(l.getTelephonenumber());
		r.setAddress(l.getAddress());
	}
	
	public static void userMap(User r, UserDTO l) {
		r.setName(l.getName());
		r.setUsername(l.getUsername());
		r.setTelephonenumber(l.getTelephonenumber());
		r.setAddress(l.getAddress());
	}
	public static void foodMap(Food r, FoodDTO l) {
		r.setId(l.getId());
		r.setName(l.getName());
		r.setPrice(l.getPrice());
		r.setNumber(l.getNumber());
		r.setImage(l.getImage());
	}
	public static void foodMap(FoodDTO r, Food l) {
		r.setId(l.getId());
		r.setName(l.getName());
		r.setPrice(l.getPrice());
		r.setNumber(l.getNumber());
		r.setImage(l.getImage());
		if(l.getFoodCategory()!=null) r.setCategory(l.getFoodCategory().getName());
	}
	
	public static void BillMap(BillDTO r, Bill l) {
		if(l.getOrder()!=null) r.setIdroom(l.getOrder().getRoom().getId()+"");
		r.setId(l.getId());
		if(l.getOrder()!=null) r.setNameEmployee(l.getOrder().getUser().getName());
		if(l.getOrder()!=null) r.setNameCustomer(l.getOrder().getCustomerName());
		r.setDate(l.getDate());
		if(l.getOrder()!=null) r.setStartHour(l.getOrder().getStarthour());
		if(l.getOrder()!=null) r.setEndHour(l.getOrder().getEndhour());
		if(l.getOrder()!=null) r.setRoom(l.getOrder().getRoom().getName());
		r.setHourprice(l.getHourprice());
		r.setFoodprice(l.getFoodprice());
		r.setTotalprice(l.getTotalprice());
		r.setStatus(l.isStatus()?"1":"0");
		r.setDate(l.getDate());
		if(l.getOrder()!=null) r.setOrderdate(l.getOrder().getOrderdate());
		if(l.getDate()!=null) r.setSdate(l.getDate().toString());
		if(l.getOrder()!=null) r.setTotalHour(l.getOrder().getTotalhour());
		if(l.getOrder()!=null) r.setNote(l.getOrder().getNote());
		if(l.getOrder()!=null && l.getOrder().getOrderFoods()!=null && l.getOrder().getOrderFoods().size()>0) {
			List<OrderFood> lfood = l.getOrder().getOrderFoods();
			List<FoodDTO> lfFoodDTO = new ArrayList<FoodDTO>();
			for (OrderFood food : lfood) {
				FoodDTO foodDTO = new FoodDTO();
				foodMap(foodDTO, food.getFood());
				foodDTO.setIdOrderfood(food.getId());
				lfFoodDTO.add(foodDTO);
			}
			r.setFoods(lfFoodDTO);
		}
	}
	public static void OrderMap(Order r, BillDTO l) {
		r.setOrderdate(l.getDate());
		r.setCustomerName(l.getNameCustomer());
		r.setEndhour(l.getEndHour());
		r.setStarthour(l.getStartHour());
		r.setNote(l.getNote());
	}
}
