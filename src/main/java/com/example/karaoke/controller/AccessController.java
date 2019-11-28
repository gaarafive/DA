package com.example.karaoke.controller;

import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.karaoke.Service.MyUserDetailsService;
import com.example.karaoke.dto.UserDTO;
import com.example.karaoke.entity.Food;

@Controller
public class AccessController implements ErrorController {
	@Autowired
	MyUserDetailsService user;
	
	@RequestMapping("/login-success")
	public String welcome(Authentication authentication)
	{
		if(user.hasRole("ROLE_ADMIN")) {
			return "redirect:/admin/list-user";
		}
		else if(user.hasRole("ROLE_ACCOUNTANT"))
			return "redirect:/admin/revenue/list-revenue-year/2019";
		else return "redirect:/employee/home";
	}
	
	@RequestMapping(value = "/login")
	public String loginPage(Model model)
	{
		UserDTO user = new UserDTO();
		model.addAttribute("user",user);
		return "login-page";
	}
	@RequestMapping(value = "")
	public String loginPage2(Model model)
	{
		UserDTO user = new UserDTO();
		model.addAttribute("user",user);
		return "login-page";
	}
	
	@RequestMapping("/logout-success")
	public String logoutPage()
	{
		return "logout";
	}
	 @RequestMapping("/error")
	 public String handleError(HttpServletRequest request) {
	     Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
	     if (status != null) {
	         Integer statusCode = Integer.valueOf(status.toString());

	         if(statusCode == HttpStatus.NOT_FOUND.value()) {
	             return "error/page_404";
	         }
	         else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
	             return "error/page_500";
	         }
	     }
	     return "error/page_403";
	 }

	@Override
	public String getErrorPath() {
		// TODO Auto-generated method stub
		return "/error";
	}
}
