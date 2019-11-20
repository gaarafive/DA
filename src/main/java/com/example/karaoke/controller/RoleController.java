package com.example.karaoke.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.karaoke.Service.RoleService;
import com.example.karaoke.entity.Role;

@Controller
@RequestMapping("/admin/")
public class RoleController {
	@Autowired
	RoleService rService;
	@RequestMapping("list-role")
	public String listUser(Authentication authentication, Model model)
	{
		List<Role> roles = rService.getAll();
		model.addAttribute("roles", roles);
		return "admin/list-roles";
	}
	@RequestMapping("add-role")
	public String addRole(Model model)
	{
		Role role = new Role();
		model.addAttribute("role", role);
		return "admin/add-role";
	}
	@RequestMapping(value = "add-role" , method=RequestMethod.POST )
    public String addRole(@ModelAttribute("role") @Valid Role role, Model model)
	{
        if(rService.save(role)!=null) {
        	model.addAttribute("reponsecode","1");
        }
        else model.addAttribute("reponsecode","0");
        model.addAttribute("role",role);
        return "admin/add-role";
    }
	@RequestMapping(value = "edit-role" , method=RequestMethod.POST )
    public String editRole(@ModelAttribute("role") @Valid Role role, Model model)
	{
        rService.save(role);
        model.addAttribute("reponsecode","1");
        model.addAttribute("role",role);
        return "admin/edit-role";
    }
	@RequestMapping("edit-role/{id}")
	public String editRole(Model model, @PathVariable("id") int id)
	{
		Role role = rService.getById(id);
		model.addAttribute("role", role);
		return "admin/edit-role";
	}
	@RequestMapping("delete-role/{id}")
	public String delUser(Model model, @PathVariable("id") int id, RedirectAttributes attributes)
	{
		try{
			rService.remove(id);
			attributes.addFlashAttribute("reponsecode", 1);
		}
		catch (Exception e) {
			attributes.addFlashAttribute("reponsecode", 0);
		}
		return "redirect:/admin/list-role";
	}
}
