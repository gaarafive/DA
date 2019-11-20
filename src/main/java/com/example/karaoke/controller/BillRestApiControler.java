package com.example.karaoke.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.karaoke.Service.BillService;
import com.example.karaoke.Service.FoodService;
import com.example.karaoke.Service.MyUserDetailsService;
import com.example.karaoke.Service.OrderFoodService;
import com.example.karaoke.Service.OrderService;
import com.example.karaoke.Service.RoomService;
import com.example.karaoke.dto.AddBillDTO;
import com.example.karaoke.dto.BillDTO;
import com.example.karaoke.dto.FoodDTO;
import com.example.karaoke.dto.Mapping;
import com.example.karaoke.dto.Reponse;
import com.example.karaoke.entity.Bill;
import com.example.karaoke.entity.Food;
import com.example.karaoke.entity.Order;
import com.example.karaoke.entity.OrderFood;
import com.example.karaoke.entity.Room;
import com.example.karaoke.entity.User;

@RestController
public class BillRestApiControler {

	@Autowired
	FoodService fService;
	@Autowired
	BillService bService;
	@Autowired
	OrderService oService;
	@Autowired
	RoomService rService;
	@Autowired
	OrderFoodService oFService;
	@Autowired
	MyUserDetailsService uService;
	@RequestMapping(value = "/api/foodid", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	public Reponse<FoodDTO> getFootDTO(BillDTO food){
		List<FoodDTO> foods = new ArrayList<FoodDTO>();
		for (String id : food.getFoodid()) {
			Food f = fService.getById(Integer.parseInt(id));
			FoodDTO foodDTO = new FoodDTO();
			Mapping.foodMap(foodDTO, f);
			foods.add(foodDTO);
		}
		return new Reponse<FoodDTO>(1,"",foods);
	}
	
	@RequestMapping(value = "/api/savebill", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	public Reponse<Object> setBill(BillDTO bill){
		Order order = new Order();
		Bill nbill = new Bill();
		List<OrderFood> orderfoods = new ArrayList<OrderFood>();
		List<Food> foods2 = new ArrayList<Food>();
		nbill.setOrder(order);
		order.setBill(nbill);
		oService.save(order);
		if(bill.getFoodid()!=null) {
			for (int i = 0; i <bill.getFoodid().size();i++) {
				Food f = fService.getById(Integer.parseInt(bill.getFoodid().get(0)));
				foods2.add(f);
				OrderFood orderFood = new OrderFood();
				orderFood.setFood(f);
				orderFood.setOrder(order);
				orderFood.setNumber(Integer.parseInt(bill.getFoodnumber().get(i)));
				orderfoods.add(orderFood);
				oFService.save(orderFood);
			}
			order.setOrderFoods(orderfoods);
		} 
		try{
		    Date date1=new SimpleDateFormat("MM/dd/yyyy hh:mm a").parse(bill.getSdate()); 
		    bill.setDate(date1);
		}catch(Exception e) {}
		nbill.setDate(bill.getDate());
		Mapping.OrderMap(order, bill);
		order.setTotalhour(totalHour(order.getStarthour(),order.getEndhour()));
		//set user
		User user = uService.getById(Integer.parseInt(bill.getNameEmployee()));
		order.setUser(user);
		//set room
		Room room = rService.getById(Integer.parseInt(bill.getRoom()));
		order.setRoom(room);
		
		nbill.setDate(bill.getDate());
		int hourPrice= 0;
		try{
			hourPrice = hourPrice(bill.getStartHour()+"",bill.getEndHour()+"",room.getPrice());
			nbill.setHourprice(hourPrice);
		}
		catch(Exception e) {}
		int foodPrice= 0;
		try{
			if(bill.getFoodnumber().size()>0) {
				foodPrice = foodPrice(foods2, bill.getFoodnumber());
				nbill.setFoodprice(foodPrice);
			}
		}
		catch(Exception e) {}
		
		if(bill.getStatus()!=null) nbill.setStatus(bill.getStatus().equals("1"));
		nbill.setTotalprice(foodPrice+hourPrice);
		oService.save(order);
		return new Reponse<Object>(1,"",null);
	}
	@RequestMapping(value = "/api/saveeditbill", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	public Reponse<Object> updateBill(BillDTO bill){
		Bill nbill = bService.getById(bill.getId());
		Order order = nbill.getOrder();
			//xoa order food
			if(order.getOrderFoods()!=null) {
				for (OrderFood o : order.getOrderFoods()) {
					oFService.remove(o.getId());
				}
				order.getOrderFoods().clear();
		}
		List<OrderFood> orderfoods = new ArrayList<OrderFood>();
		List<Food> foods2 = new ArrayList<Food>();
		nbill.setOrder(order);
		order.setBill(nbill);
		if(bill.getFoodid()!=null) {
			for (int i = 0; i <bill.getFoodid().size();i++) {
				Food f = fService.getById(Integer.parseInt(bill.getFoodid().get(i)));
				foods2.add(f);
				OrderFood orderFood = new OrderFood();
				orderFood.setFood(f);
				orderFood.setOrder(order);
				orderFood.setNumber(Integer.parseInt(bill.getFoodnumber().get(i)));
				orderfoods.add(orderFood);
				oFService.save(orderFood);
			}
			order.setOrderFoods(orderfoods);
		} 
		try{
		    Date date1=new SimpleDateFormat("MM/dd/yyyy hh:mm a").parse(bill.getSdate()); 
		    bill.setDate(date1);
		}catch(Exception e) {}
		nbill.setDate(bill.getDate());
		Mapping.OrderMap(order, bill);
		order.setTotalhour(totalHour(order.getStarthour(),order.getEndhour()));
		//set user
		User user = uService.getById(Integer.parseInt(bill.getNameEmployee()));
		order.setUser(user);
		//set room
		Room room = rService.getById(Integer.parseInt(bill.getRoom()));
		order.setRoom(room);
		nbill.setDate(bill.getDate());
		int hourPrice= 0;
		try{
			hourPrice = hourPrice(bill.getStartHour()+"",bill.getEndHour()+"",room.getPrice());
			nbill.setHourprice(hourPrice);
		}
		catch(Exception e) {}
		int foodPrice= 0;
		try{
			if(bill.getFoodnumber().size()>0) {
				foodPrice = foodPrice(foods2, bill.getFoodnumber());
				nbill.setFoodprice(foodPrice);
			}
		}
		catch(Exception e) {}
		
		if(bill.getStatus()!=null) nbill.setStatus(bill.getStatus().equals("1"));
		nbill.setTotalprice(foodPrice+hourPrice);
		oService.save(order);
		return new Reponse<Object>(1,"",null);
	}
	public String totalHour(String startHour, String endHour) {
		int hours1 = Integer.parseInt((startHour.split(":")[0]));
        int minutes1 = Integer.parseInt((startHour.split(":")[1]));
        
        int hours2 = Integer.parseInt((endHour.split(":")[0]));
        int minutes2 = Integer.parseInt((endHour.split(":")[1]));
        
        int minutesstart = hours1*60+minutes1;
        int minutesend = hours2*60+minutes2;
        
        int minute = minutesend - minutesstart;
        if(minute<0) minute+=24*60;
        
        return minute + " Phút";
	}
	public int hourPrice(String startHour, String endHour, int roomPrice) {
		int hours1 = Integer.parseInt((startHour.split(":")[0]));
        int minutes1 = Integer.parseInt((startHour.split(":")[1]));
        
        int hours2 = Integer.parseInt((endHour.split(":")[0]));
        int minutes2 = Integer.parseInt((endHour.split(":")[1]));
        
        int minutesstart = hours1*60+minutes1;
        int minutesend = hours2*60+minutes2;
        
        int minute = minutesend - minutesstart;
        if(minute<0) minute+=24*60;
        
        return (minute*roomPrice)/60;
	}
	public int foodPrice(List<Food> foods,List<String> numbers) {
		int total = 0;
		for (int i =0;i < foods.size();i++) {
			total += foods.get(i).getPrice() * Integer.parseInt(numbers.get(i));
		}
		return total;
	}
}
