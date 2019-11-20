package com.example.karaoke.controller;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.karaoke.Service.BillService;
import com.example.karaoke.Service.FoodCategoryService;
import com.example.karaoke.Service.FoodService;
import com.example.karaoke.Service.MyUserDetailsService;
import com.example.karaoke.Service.OrderFoodService;
import com.example.karaoke.Service.OrderService;
import com.example.karaoke.Service.RoomService;
import com.example.karaoke.dto.BillDTO;
import com.example.karaoke.dto.Mapping;
import com.example.karaoke.entity.Bill;
import com.example.karaoke.entity.Food;
import com.example.karaoke.entity.FoodCategory;
import com.example.karaoke.entity.Order;
import com.example.karaoke.entity.OrderFood;
import com.example.karaoke.entity.Room;
import com.example.karaoke.entity.User;


@Controller
@RequestMapping("/employee/")
public class EmployeeController {
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
	@Autowired
	FoodCategoryService fcService;
	
	@RequestMapping(value = "/home" , method=RequestMethod.GET )
	public String home(Authentication authentication, Model model)
	{
		List<FoodCategory> foodCategories = new ArrayList<FoodCategory>();
		foodCategories = fcService.getAll();
		model.addAttribute("foodCategories", foodCategories);
		model.addAttribute("idCategory", -1);
		User currentUser = uService.findByUsername(authentication.getName());
		model.addAttribute("nameofuser", currentUser.getName());
		List<Room> rooms = new ArrayList<Room>();
		rooms = rService.getAll();
		model.addAttribute("rooms", rooms);
		List<Food> foods = new ArrayList<Food>();
		foods = fService.getAll();
		model.addAttribute("foods", foods);
		BillDTO bill = new BillDTO();
		model.addAttribute("bill", bill);
		Room room = new Room();
		model.addAttribute("room", room);
		return "employee/home";
	}
	@RequestMapping(value = "/home/{valueroom}" , method=RequestMethod.GET )
	public String searchroom(@PathVariable String valueroom,Authentication authentication, Model model)
	{
		User currentUser = uService.findByUsername(authentication.getName());
		model.addAttribute("nameofuser", currentUser.getName());
		List<Room> rooms = new ArrayList<Room>();
		rooms = rService.findByNameContaining(valueroom);
		model.addAttribute("searchroom", valueroom);
		model.addAttribute("rooms", rooms);
		List<Food> foods = new ArrayList<Food>();
		foods = fService.getAll();
		model.addAttribute("foods", foods);
		BillDTO bill = new BillDTO();
		model.addAttribute("bill", bill);
		Room room = new Room();
		model.addAttribute("room", room);
		return "employee/home";
	}
	@RequestMapping("/content1")
    public String getContent1() {
        return "employee/emp_fragments/left-content :: content2";
    }
	@RequestMapping(value = "/load/content-food/{valuefood}", method = RequestMethod.GET)
	public String loadSearchFood(@PathVariable String valuefood, Model model)
	{
		try {
			List<Food> foods = new ArrayList<Food>();
			if(!valuefood.equals("-1")) 
				foods = fService.findByNameContaining(valuefood);
			else foods = fService.getAll();
			model.addAttribute("foods", foods);
			List<FoodCategory> foodCategories = new ArrayList<FoodCategory>();
			foodCategories = fcService.getAll();
			model.addAttribute("foodCategories", foodCategories);
			model.addAttribute("idCategory", -1);
		}
		catch (Exception e) {
		}
		return "employee/emp_fragments/left-content :: content-food";
	}
	
	@RequestMapping(value = "/load/content-food-with-category/{id}", method = RequestMethod.GET)
	public String loadFoodCategory(@PathVariable int id, Model model)
	{
		try {
			List<FoodCategory> foodCategories = new ArrayList<FoodCategory>();
			foodCategories = fcService.getAll();
			model.addAttribute("foodCategories", foodCategories);
			model.addAttribute("idCategory", id);
			List<Food> foods = new ArrayList<Food>();
			if(id!=-1) 
				foods = fService.findByCategoryId(id);
			else foods = fService.getAll();
			model.addAttribute("foods", foods);
		}
		catch (Exception e) {
		}
		return "employee/emp_fragments/left-content :: content-food";
	}
    @RequestMapping(value = "/load/content-order-detail/{idroom}", method = RequestMethod.GET)
	public String Orderdetails(@PathVariable int idroom, Model model)
	{
		try {
			List<Order> od = oService.findOrderOfRoomWithStatusIsactive(false, idroom);
			Order order = new Order();
			order = od.get(0);
			SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm");
			if(order.getEndhour().isEmpty()) {
			 order.setTotalhour(totalHour(order.getStarthour(),hourFormat.format(new Date())));
			}
			Bill bill = order.getBill();
			BillDTO billDTO = new BillDTO();
			if(od.size()>0)
				Mapping.BillMap(billDTO, bill );
			model.addAttribute("bill", billDTO);
			List<Room> rooms = new ArrayList<Room>();
			rooms = rService.findRoomWithIsActive(true);
			model.addAttribute("rooms", rooms);
		}
		catch (Exception e) {
		}
		return "employee/emp_fragments/right-content :: content-order-detail";
	}
    @RequestMapping(value = "/load/content-menu-food/{idroom}/{idfood}", method = RequestMethod.GET)
	public String showMenuFood(@PathVariable int idroom, @PathVariable int idfood, Model model)
	{
		try {
			List<Order> orders = oService.findOrderOfRoomWithStatusIsactive(false, idroom);
			Order order = orders.get(0);
			SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm");
			if(order.getEndhour().isEmpty()) {
			 order.setTotalhour(totalHour(order.getStarthour(),hourFormat.format(new Date())));
			}
			List<OrderFood> listOrderFoods = order.getOrderFoods();
			Room room = rService.getById(idroom);
			
			if(idfood>0) {
				Food food = fService.getById(idfood);
				List<OrderFood> orderfoodhads = oFService.findOrderFood(order.getId(), idfood);
				if(orderfoodhads!=null&&orderfoodhads.size()>0) {
					OrderFood orderFood = orderfoodhads.get(0);
					orderFood.setNumber(orderFood.getNumber()+1);
					orderFood.setPrice(orderFood.getNumber()*orderFood.getFood().getPrice());
					oFService.save(orderFood);
					//giam so luong
					for (int i = 0; i < listOrderFoods.size(); i++) {
						if(listOrderFoods.get(i).getId() == orderFood.getId()) {
							listOrderFoods.get(i).setNumber(orderFood.getNumber());
						}
					}
				}
				else {
					OrderFood orderFood = new OrderFood();
					orderFood.setFood(food);
					orderFood.setOrder(order);
					orderFood.setNumber(1);
					orderFood.setPrice(food.getPrice());
					//giam so luong
					oFService.save(orderFood);
					listOrderFoods.add(orderFood);
					order.setOrderFoods(listOrderFoods);
					oService.save(order);
				}
				int numberfood = Integer.parseInt(food.getNumber())-1;
				food.setNumber(numberfood+"");
				fService.save(food);
			}
			Bill bill = order.getBill();
			//set price
			if(order.getTotalhour()!=null) bill.setHourprice(Integer.parseInt(order.getTotalhour())*order.getRoom().getPrice()/60);
			
			BillDTO billDTO = new BillDTO();
			Mapping.BillMap(billDTO, bill );
			int foodprice = 0;
			for (int i = 0; i < listOrderFoods.size(); i++) {
				billDTO.getFoods().get(i).setNumberfood(listOrderFoods.get(i).getNumber());
				billDTO.getFoods().get(i).setIdOrderfood(listOrderFoods.get(i).getId());
				foodprice += listOrderFoods.get(i).getNumber()*listOrderFoods.get(i).getFood().getPrice();
			}
			billDTO.setFoodprice(foodprice);
			bill.setFoodprice(foodprice);
			bill.setTotalprice(bill.getFoodprice()+bill.getHourprice());
			
			bService.save(bill);
			model.addAttribute("bill", billDTO);
		}
		catch (Exception e) {
		}
		return "employee/emp_fragments/right-content :: content-menu-food";
	}
    
    
    
    @RequestMapping(value = "/delete-orderfood/{idroom}/{id}", method = RequestMethod.GET)
	public String DeleteOrderfood(@PathVariable int idroom, @PathVariable int id,Model model)
	{
		try {
			List<Order> orders = oService.findOrderOfRoomWithStatusIsactive(false, idroom);
			Order order = orders.get(0);
			List<OrderFood> news= order.getOrderFoods();
			for (OrderFood orderFood : news) {
				if(orderFood.getId() == id) {
					Food food = orderFood.getFood();
					food.setNumber((Integer.parseInt(food.getNumber())+ orderFood.getNumber())+"");
					fService.save(food);
					news.remove(orderFood);
					break;
				}
			}
			order.setOrderFoods(news);
			oService.save(order);
			
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "redirect:/employee/load/content-menu-food/"+idroom+"/-1";
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
}
