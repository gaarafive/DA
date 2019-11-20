package com.example.karaoke.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;
import com.example.karaoke.Service.MyUserDetailsService;
import com.example.karaoke.entity.User;

@ControllerAdvice(annotations = RestController.class)
public class AnnotationAdvice {

     @Autowired
     MyUserDetailsService userService;

     @ModelAttribute("currentUser")
     public String getCurrentUser() {
         UserDetails userDetails = (UserDetails) 
         SecurityContextHolder.getContext()
               .getAuthentication().getPrincipal();

      return userService.findByUsername(userDetails.getUsername()).getName();
     }
  }