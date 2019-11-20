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

import com.example.karaoke.Service.FoodCategoryService;
import com.example.karaoke.entity.FoodCategory;

@Controller
@RequestMapping("/admin/")
public class FoodCategoryController {
	@Autowired
	FoodCategoryService rService;
	@RequestMapping("list-foodc")
	public String listUser(Authentication authentication, Model model)
	{
		List<FoodCategory> foodcs = rService.getAll();
		model.addAttribute("foodcs", foodcs);
		return "admin/list-foodcs";
	}
	@RequestMapping("add-foodc")
	public String addFoodCategory(Model model)
	{
		FoodCategory foodc = new FoodCategory();
		model.addAttribute("foodc", foodc);
		return "admin/add-foodc";
	}
	@RequestMapping(value = "add-foodc" , method=RequestMethod.POST )
    public String addFoodCategory(@ModelAttribute("foodc") @Valid FoodCategory foodc, Model model)
	{
        rService.save(foodc);
        model.addAttribute("reponsecode","1");
        model.addAttribute("foodc",foodc);
        return "admin/add-foodc";
    }
	@RequestMapping(value = "edit-foodc" , method=RequestMethod.POST )
    public String editFoodCategory(@ModelAttribute("foodc") @Valid FoodCategory foodc, Model model)
	{
        rService.save(foodc);
        model.addAttribute("reponsecode","1");
        model.addAttribute("foodc",foodc);
        return "admin/edit-foodc";
    }
	@RequestMapping("edit-foodc/{id}")
	public String editFoodCategory(Model model, @PathVariable("id") int id)
	{
		FoodCategory foodc = rService.getById(id);
		model.addAttribute("foodc", foodc);
		return "admin/edit-foodc";
	}
	@RequestMapping("delete-foodc/{id}")
	public String delUser(Model model, @PathVariable("id") int id, RedirectAttributes attributes)
	{
		try{
			rService.remove(id);
			attributes.addFlashAttribute("reponsecode", 1);
		}
		catch (Exception e) {
			attributes.addFlashAttribute("reponsecode", 0);
		}
		return "redirect:/admin/list-foodc";
	}
}
