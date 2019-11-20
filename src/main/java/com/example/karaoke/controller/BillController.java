package com.example.karaoke.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.print.attribute.standard.DateTimeAtCompleted;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.karaoke.Service.BillService;
import com.example.karaoke.Service.FoodService;
import com.example.karaoke.Service.MyUserDetailsService;
import com.example.karaoke.Service.OrderFoodService;
import com.example.karaoke.Service.OrderService;
import com.example.karaoke.Service.RoomService;
import com.example.karaoke.dto.BillDTO;
import com.example.karaoke.dto.FoodDTO;
import com.example.karaoke.dto.Mapping;
import com.example.karaoke.entity.Bill;
import com.example.karaoke.entity.Food;
import com.example.karaoke.entity.Order;
import com.example.karaoke.entity.OrderFood;
import com.example.karaoke.entity.Room;
import com.example.karaoke.entity.User;

@Controller
@RequestMapping("/admin/")
public class BillController {
	@Autowired
	BillService rService;
	@Autowired
	RoomService roService;
	@Autowired
	FoodService fService;
	@Autowired
	OrderFoodService ofService;
	@Autowired
	OrderService oService;
	@Autowired
	MyUserDetailsService uService;
	@GetMapping("list-bill")
	public String listUser(Authentication authentication, Model model)
	{
		Date date = new Date();
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int month = localDate.getMonthValue();
		int day = localDate.getDayOfMonth();
		int year = localDate.getYear();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date date1 = null;
		try {
			date1 = simpleDateFormat.parse(month+"/"+day+"/"+(year-1));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		String datefilter = simpleDateFormat.format(date1) + " - " + simpleDateFormat.format(new Date());
		
		List<Bill> bills = rService.findAllWithStartDateEnDate(date1,new Date());
		List<BillDTO> billDTOs = new ArrayList<BillDTO>();
		for (Bill bill : bills) {
			BillDTO bDto = new BillDTO();
			Mapping.BillMap(bDto, bill);
			billDTOs.add(bDto);
		}
		model.addAttribute("datefilter", datefilter);
		model.addAttribute("bills", billDTOs);
		return "admin/list-bills";
	}
	@PostMapping("list-bill")
	public String pListUser(String datefilter,Authentication authentication, Model model)
	{
		Date startdate = null;
		Date enddate = null;
		try {
			 startdate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(datefilter.split("-")[0].trim()+" 00:00:00");
			 enddate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(datefilter.split("-")[1].trim()+" 23:59:59");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Bill> bills = rService.findAllWithStartDateEnDate(startdate, enddate);
		List<BillDTO> billDTOs = new ArrayList<BillDTO>();
		for (Bill bill : bills) {
			BillDTO bDto = new BillDTO();
			Mapping.BillMap(bDto, bill);
			billDTOs.add(bDto);
		}
		model.addAttribute("datefilter", datefilter);
		model.addAttribute("bills", billDTOs);
		return "admin/list-bills";
	}
	@RequestMapping("add-bill")
	public String addBill(Model model, Authentication authentication)
	{
		List<Food> foods = fService.getAll();
		model.addAttribute("foods", foods);
		BillDTO bill = new BillDTO();
		List<Room> rooms = roService.getAll();
		model.addAttribute("rooms", rooms);
		User currentUser = uService.findByUsername(authentication.getName());
		model.addAttribute("currentUser", currentUser);
		List<User> users = uService.getAll();
		users.remove(currentUser);
		model.addAttribute("users", users);
		model.addAttribute("bill", bill);
		return "admin/add-bill";
	}
	@RequestMapping(value = "add-bill", method=RequestMethod.POST)
	public String addBill2(Model model, Authentication authentication)
	{
		List<Food> foods = fService.getAll();
		model.addAttribute("foods", foods);
		BillDTO bill = new BillDTO();
		List<Room> rooms = roService.getAll();
		model.addAttribute("rooms", rooms);
		User currentUser = uService.findByUsername(authentication.getName());
		model.addAttribute("currentUser", currentUser);
		List<User> users = uService.getAll();
		users.remove(currentUser);
		model.addAttribute("users", users);
		model.addAttribute("bill", bill);
		return "admin/add-bill";
	}
	@RequestMapping(value = "edit-bill" , method=RequestMethod.POST )
    public String editBill(@ModelAttribute("bill") @Valid Bill bill, Model model)
	{
        rService.save(bill);
        model.addAttribute("reponsecode","1");
        model.addAttribute("bill",bill);
        return "admin/edit-bill";
    }
	@RequestMapping("edit-bill/{id}")
	public String editBill(Model model, @PathVariable("id") int id)
	{
		Bill bill = rService.getById(id);
		List<Room> rooms = roService.getAll();
		model.addAttribute("rooms", rooms);
		User currentUser = bill.getOrder().getUser();
		model.addAttribute("currentUser", currentUser);
		List<User> users = uService.getAll();
		users.remove(currentUser);
		model.addAttribute("users", users);
		List<Food> foodall = new ArrayList<Food>();
		foodall = fService.getAll();
		model.addAttribute("foodall", foodall);
		List<FoodDTO> foods = new ArrayList<FoodDTO>();
		
		if(bill.getOrder()!=null&&bill.getOrder().getOrderFoods()!=null) {
			for (int i = 0; i <bill.getOrder().getOrderFoods().size();i++) {
				OrderFood orderFood = bill.getOrder().getOrderFoods().get(i);
				Food f = orderFood.getFood();
				FoodDTO fDto = new FoodDTO();
				Mapping.foodMap(fDto, f);
				fDto.setNumberfood(orderFood.getNumber());
				foods.add(fDto);
			}
		} 
		model.addAttribute("foods", foods);
		BillDTO billDTO = new BillDTO();
		Mapping.BillMap(billDTO, bill);
		model.addAttribute("bill", billDTO);
		return "admin/edit-bill";
	}
	@RequestMapping("delete-bill/{id}")
	public String delUser(Model model, @PathVariable("id") int id, RedirectAttributes attributes)
	{
		try{
			
			Order order = rService.getById(id).getOrder();
			
			if(order.getOrderFoods()!=null) {
					for (OrderFood o : order.getOrderFoods()) {
						ofService.remove(o.getId());
					}
					order.getOrderFoods().clear();
			}
			
			rService.remove(id);
			attributes.addFlashAttribute("reponsecode", 1);
		}
		catch (Exception e) {
			attributes.addFlashAttribute("reponsecode", 0);
		}
		return "redirect:/admin/list-bill";
	}
	
	
}
