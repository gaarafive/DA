package com.example.karaoke.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.karaoke.Service.BillService;
import com.example.karaoke.Service.FoodService;
import com.example.karaoke.Service.MyUserDetailsService;
import com.example.karaoke.Service.OrderFoodService;
import com.example.karaoke.Service.OrderService;
import com.example.karaoke.Service.RoomService;
import com.example.karaoke.dto.BillDTO;
import com.example.karaoke.dto.Mapping;
import com.example.karaoke.dto.Reponse;
import com.example.karaoke.entity.Bill;
import com.example.karaoke.entity.Food;
import com.example.karaoke.entity.Order;
import com.example.karaoke.entity.OrderFood;
import com.example.karaoke.entity.Room;
import com.example.karaoke.entity.User;

@RestController
public class EmployeeRestApiController {
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
	
	@RequestMapping(value = "/api/openroom", method = RequestMethod.POST)
	public Reponse Openroom(BillDTO bill,Authentication authentication)
	{
		SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm");
		Date now = new Date();
		if(bill.getStartHour().isEmpty()) {
		    String hour = hourFormat.format(now);
			bill.setStartHour(hour);
		}
		Order order = new Order();
		Mapping.OrderMap(order, bill);
		User user = uService.findByUsername(authentication.getName());
		if(user!=null) order.setUser(user);
		Bill obill = new Bill();
		if(bill.getRoom()!=null) {
			Room room = rService.getById(Integer.parseInt(bill.getRoom()));
			room.setStatus(true);
			order.setRoom(room);
			rService.save(room);
		}
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
	    String d = dateFormat.format(calendar.getTime());
	    Date currentdate = null;
		try {
			currentdate = dateFormat.parse(d);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		order.setOrderdate(currentdate);
		order.setBill(obill);
		if(!order.getEndhour().isEmpty()) {
			order.setTotalhour(totalHour(bill.getStartHour(),bill.getEndHour()));
		}
		else order.setTotalhour(totalHour(bill.getStartHour(),hourFormat.format(new Date())));
		obill.setOrder(order);
		
		try {
			oService.save(order);
		}
		catch (Exception e) {
			return new Reponse<>(0, "error", null);
		}
		return new Reponse<>(1, "mở bill", null);
	}
	@RequestMapping(value = "/api/save-openroom", method = RequestMethod.POST)
	public Reponse SaveOpenroom(BillDTO bill,Authentication authentication)
	{
		Order order = new Order();
		Bill bill2 = bService.getById(bill.getId());
		if(bill2.getOrder()!=null) order = bill2.getOrder();
		else {
				List<Order> orders = oService.findOrderOfRoomWithStatusIsactive(false, Integer.parseInt(bill.getIdroom()));
				order = orders.get(0);
		}
		Mapping.OrderMap(order, bill);
		User user = uService.findByUsername(authentication.getName());
		if(user!=null) order.setUser(user);
		if(bill.getRoom()!=null) {
			Room room = rService.getById(Integer.parseInt(bill.getRoom()));
			room.setStatus(true);
			order.setRoom(room);
			rService.save(room);
		}
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
	    String d = dateFormat.format(calendar.getTime());
	    Date currentdate = null;
		try {
			currentdate = dateFormat.parse(d);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		order.setOrderdate(currentdate);
		SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm");
		if(!order.getEndhour().isEmpty()) {
			order.setTotalhour(totalHour(bill.getStartHour(),bill.getEndHour()));
		}
		else order.setTotalhour(totalHour(bill.getStartHour(),hourFormat.format(new Date())));
		
		try {
			oService.save(order);
		}
		catch (Exception e) {
			return new Reponse<>(0, "error", null);
		}
		return new Reponse<>(1, "mở bill", null);
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
        
        return minute+"";
	}
	
	@RequestMapping(value = "/api/orderdetails/{id}", method = RequestMethod.GET)
	public Reponse<BillDTO> Orderdetails(@PathVariable int id)
	{
		List<BillDTO> billDTOs = new ArrayList<BillDTO>();
		List<Order> od = null;
		try {
			od = oService.findOrderOfRoomWithStatusIsactive(false, id);
			
			BillDTO billDTO = new BillDTO();
			if(od!=null) Mapping.BillMap(billDTO, od.get(0).getBill());
			billDTOs.add(billDTO);
		}
		
		catch (Exception e) {
			return new Reponse<>(0, "error", null);
		}
		return new Reponse<>(1, "orderdetails", billDTOs);
	}
	
	@RequestMapping(value = "/employee/changenumberfood/{id}/{number}", method = RequestMethod.GET)
	public void changenumber(@PathVariable int id,@PathVariable int number)
	{
    	OrderFood orderFood = oFService.getById(id);
    	int old = orderFood.getNumber();
    	orderFood.setNumber(number);
    	orderFood.setPrice(number*orderFood.getFood().getPrice());
    	Food food = orderFood.getFood();
    	food.setNumber(Integer.parseInt(food.getNumber())+(old-number)+"");
    	oFService.save(orderFood);
    	fService.save(food);
	}
	
	@RequestMapping(value = "/employee/thanhtoan/{id}", method = RequestMethod.GET)
	public Reponse<String> thanhtoan(@PathVariable int id)
	{
		List<Order> orders = oService.findOrderOfRoomWithStatusIsactive(false, id);
		Order order = orders.get(0);
		Bill bill = order.getBill();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
	    String d = dateFormat.format(calendar.getTime());
	    Date currentdate = null;
		try {
			currentdate = dateFormat.parse(d);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		bill.setDate(currentdate);
		bill.setStatus(true);
		
		if(order.getEndhour().isEmpty()) {
			SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm");
			order.setEndhour(hourFormat.format(new Date()));
			order.setTotalhour(totalHour(order.getStarthour(),order.getEndhour()));
			if(order.getTotalhour()!=null) bill.setHourprice(Integer.parseInt(order.getTotalhour())*order.getRoom().getPrice()/60);
			List<OrderFood> listOrderFoods = order.getOrderFoods();
			int foodprice = 0;
			for (int i = 0; i < listOrderFoods.size(); i++) {
				foodprice += listOrderFoods.get(i).getNumber()*listOrderFoods.get(i).getFood().getPrice();
			}
			bill.setFoodprice(foodprice);
			bill.setTotalprice(bill.getFoodprice()+bill.getHourprice());
			oService.save(order);
		}
		Room room = order.getRoom();
		room.setStatus(false);
		bService.save(bill);
		rService.save(room);
		return new Reponse<>(1, "ok", null);
	}
}
