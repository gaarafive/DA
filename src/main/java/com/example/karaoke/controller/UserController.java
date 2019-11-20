package com.example.karaoke.controller;

import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.validation.BindingResult;
import com.example.karaoke.Service.MyUserDetailsService;
import com.example.karaoke.dto.Mapping;
import com.example.karaoke.dto.UserDTO;
import com.example.karaoke.entity.User;

@Controller
@RequestMapping("/admin/")
public class UserController {
	@Autowired
	MyUserDetailsService userService;
	@RequestMapping("list-user")
	public String listUser(Authentication authentication, Model model)
	{
		List<User> users = userService.getAll();
		model.addAttribute("users", users);
		return "admin/list-users";
	}
	
	@RequestMapping("edit-user/{id}")
	public String elitUser(Model model, @PathVariable("id") int id)
	{
		User user = userService.getById(id);
		UserDTO userDTO = new UserDTO();
		Mapping.userMap(userDTO, user);
		
		model.addAttribute("user", userDTO);
		return "admin/edit-user";
	}
	@RequestMapping(value = "edit-user" , method=RequestMethod.POST )
    public String elitUser(@ModelAttribute("user") @Valid UserDTO userDto, Model model) {
        model.addAttribute("nameofuser", userDto.getName());
        User user = userService.findByUsername(userDto.getUsername());
        Mapping.userMap(user, userDto);
        
        user.setRole(userService.getRoleByName(userDto.getRole()));
        userService.save(user);
        model.addAttribute("reponsecode","1");
        model.addAttribute("user",userDto);
        return "admin/edit-user";
    }
	@RequestMapping("delete-user/{id}")
	public String deleteUser(@PathVariable("id") int id, RedirectAttributes attributes)
	{
		try{
			userService.remove(id);
			attributes.addFlashAttribute("reponsecode", 1);
		}
		catch (Exception e) {
			attributes.addFlashAttribute("reponsecode", 0);
		}
		
		return "redirect:/admin/list-user";
	}
	@RequestMapping("add-user")
	public String elitUser(Model model)
	{
		UserDTO userDTO = new UserDTO();
		model.addAttribute("user", userDTO);
		return "admin/add-user";
	}

	@RequestMapping(value = "add-user" , method=RequestMethod.POST )
    public String addUser(@ModelAttribute("user") @Valid UserDTO userDto, Model model, BindingResult result) {
        model.addAttribute("nameofuser", userDto.getName());
        User user = userService.findByUsername(userDto.getUsername());
        if (result.hasErrors()) {
        	model.addAttribute("reponsecode","0");
            return "admin/add-user";
        }
        if(user!=null) {
        	model.addAttribute("reponsecode","0");
        	model.addAttribute("reponsemessage","User name existed!");
        	return "admin/add-user";
        }
        
        userService.registerNewUserAccount(userDto);
        model.addAttribute("reponsecode","1");
        model.addAttribute("user",userDto);
        return "admin/add-user";
    }
	
}
