package com.example.karaoke.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.karaoke.Service.MyUserDetailsService;
import com.example.karaoke.dto.Mapping;
import com.example.karaoke.dto.UserDTO;
import com.example.karaoke.entity.User;
@Controller
@RequestMapping("/admin/")
public class AdminController {
	@Autowired
	MyUserDetailsService usersv;
	@Autowired
	BCryptPasswordEncoder pw;
	@RequestMapping("index")
	public String admin(Authentication authentication, Model model)
	{
		User currentUser = usersv.findByUsername(authentication.getName());
		model.addAttribute("nameofuser", currentUser.getName());
		return "admin/plain-page";
	}
	@RequestMapping("profile")
	public String profile(Authentication authentication, Model model)
	{
		User currentUser = usersv.findByUsername(authentication.getName());
		UserDTO user = new UserDTO();
		Mapping.userMap(user, currentUser);
		model.addAttribute("user",user);
		model.addAttribute("nameofuser", currentUser.getName());
		return "admin/profile";
	}
	@RequestMapping(value = "profile" , method=RequestMethod.POST )
    public String profile(@ModelAttribute("user") @Valid UserDTO userDto,
        BindingResult result, Model model) {
        model.addAttribute("nameofuser", userDto.getName());
        User user = usersv.findByUsername(userDto.getUsername());
        Mapping.userMap(user, userDto);
        usersv.save(user);
        model.addAttribute("reponsecode","1");
        model.addAttribute("user",user);
        return "admin/profile";
    }
	
	@RequestMapping("changepassword")
	public String changepassword(Authentication authentication, Model model)
	{
		User currentUser = usersv.findByUsername(authentication.getName());
		UserDTO user = new UserDTO();
		Mapping.userMap(user, currentUser);
		model.addAttribute("user",user);
		model.addAttribute("nameofuser", currentUser.getName());
		return "admin/change-password";
	}
	@RequestMapping(value = "changepassword" , method=RequestMethod.POST )
    public String changepassword(@ModelAttribute("user") @Valid UserDTO userDto,
        BindingResult result, Model model) {
        
        User user = usersv.findByUsername(userDto.getUsername());
        if(!pw.matches(userDto.getOldpassword(), user.getPassword())) {
        	model.addAttribute("errorpassword", "Incorrect password");
        	return "admin/change-password";
        }
        usersv.save(user);
        model.addAttribute("reponsecode","1");
        user.setPassword(pw.encode(userDto.getPassword()));
        model.addAttribute("user",user);
        return "admin/change-password";
    }
	
}
