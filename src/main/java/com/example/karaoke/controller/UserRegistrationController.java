package com.example.karaoke.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.karaoke.Service.MyUserDetailsService;
import com.example.karaoke.dto.UserDTO;
import com.example.karaoke.entity.User;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {

    @Autowired
    private MyUserDetailsService userService;

    @ModelAttribute("user")
    public UserDTO userRegistrationDto() {
        return new UserDTO();
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
    	UserDTO newStudent = new UserDTO();
		model.addAttribute("user",newStudent);
        return "registration.html";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") @Valid UserDTO userDto,
        BindingResult result) {

        User existing = userService.findByUsername(userDto.getUsername());
        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }

        if (result.hasErrors()) {
            return "registration";
        }

        userService.registerNewUserAccount(userDto);
        return "redirect:/login#signup?success";
    }
}