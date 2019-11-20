package com.example.karaoke.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.print.attribute.standard.DateTimeAtCompleted;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
public class RevenueController {
	@Autowired
	BillService rService;
	@Autowired
	RoomService roService;
	@Autowired
	FoodService fService;
	@Autowired
	OrderFoodService ofService;
	@Autowired
	MyUserDetailsService uService;
	@Autowired
	OrderService oService;
	@RequestMapping(value = {"list-revenue-year/{year}"})
	public String listRevenue(@PathVariable int year, Model model)
	{
		List<Order> orders = oService.findAllWithYear(year);
		List<BillDTO> billDTOs = new ArrayList<BillDTO>();
		int total = 0;
		for (Order order : orders) {
			Bill bill = order.getBill();
			BillDTO bDto = new BillDTO();
			Mapping.BillMap(bDto, bill);
			total += bill.getTotalprice();
			billDTOs.add(bDto);
			
		}
		model.addAttribute("bills", billDTOs);
		model.addAttribute("alltotal", total);
		model.addAttribute("year", year);
		model.addAttribute("month", 0);
		return "admin/list-revenue-year";
	}
	
	@RequestMapping(value = {"list-revenue/{year}/{month}"})
	public String listRevenueym(@PathVariable int year, @PathVariable int month, Model model)
	{
		List<Order> orders = oService.findAllWithYearAndMonth(year, month);
		List<BillDTO> billDTOs = new ArrayList<BillDTO>();
		int total = 0;
		for (Order order : orders) {
			Bill bill = order.getBill();
			BillDTO bDto = new BillDTO();
			Mapping.BillMap(bDto, bill);
			total += bill.getTotalprice();
			billDTOs.add(bDto);
			
		}
		model.addAttribute("bills", billDTOs);
		model.addAttribute("alltotal", total);
		model.addAttribute("year", year);
		model.addAttribute("month", month);
		return "admin/list-revenue-year";
	}
	@RequestMapping(value = {"list-revenue-day"})
	public String listRevenueDay(Model model)
	{
		Date now = new Date();
		String datefilter = new SimpleDateFormat("MM/dd/yyyy").format(now) + " - " + new SimpleDateFormat("MM/dd/yyyy").format(now);;
		String date = new SimpleDateFormat("MM/dd/yyyy").format(now);
		Date startdate = null;
		Date enddate = null;
		try {
			startdate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(datefilter.split("-")[0].trim()+" 00:00:00");
			 enddate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(datefilter.split("-")[1].trim()+" 23:59:59");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Order> orders = oService.findAllWithStartDateEnDate(startdate,enddate);
		List<BillDTO> billDTOs = new ArrayList<BillDTO>();
		int total = 0;
		for (Order order : orders) {
			Bill bill = order.getBill();
			BillDTO bDto = new BillDTO();
			Mapping.BillMap(bDto, bill);
			total += bill.getTotalprice();
			billDTOs.add(bDto);
			
		}
		model.addAttribute("bills", billDTOs);
		model.addAttribute("alltotal", total);
		model.addAttribute("datefilter", datefilter);
		return "admin/list-revenue-day";
	}
	
	@PostMapping(value = {"list-revenue-day"})
	public String plistRevenueDay(String datefilter, Model model)
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
		List<Order> orders = oService.findAllWithStartDateEnDate(startdate, enddate);
		List<BillDTO> billDTOs = new ArrayList<BillDTO>();
		int total = 0;
		for (Order order : orders) {
			Bill bill = order.getBill();
			BillDTO bDto = new BillDTO();
			Mapping.BillMap(bDto, bill);
			total += bill.getTotalprice();
			billDTOs.add(bDto);
			
		}
		model.addAttribute("bills", billDTOs);
		model.addAttribute("alltotal", total);
		model.addAttribute("datefilter", datefilter);
		return "admin/list-revenue-day";
	}
}
