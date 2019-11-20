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
import com.example.karaoke.Service.FoodService;
import com.example.karaoke.Service.UploadFileService;
import com.example.karaoke.dto.FoodDTO;
import com.example.karaoke.dto.Mapping;
import com.example.karaoke.entity.Food;
import com.example.karaoke.entity.FoodCategory;

@Controller
@RequestMapping("/admin/")
public class FoodController {
	@Autowired
	FoodService rService;
	
	@Autowired
	FoodCategoryService fService;
	@Autowired
	UploadFileService upload;
	@RequestMapping("list-food")
	public String listfood(Authentication authentication, Model model)
	{
		List<Food> foods = rService.getAll();
		model.addAttribute("foods", foods);
		return "admin/list-foods";
	}
	@RequestMapping("add-food")
	public String addfood(Model model)
	{
		List<FoodCategory> categories = fService.getAll();
		model.addAttribute("categories", categories);
		FoodDTO food = new FoodDTO();
		model.addAttribute("food", food);
		
		return "admin/add-food";
	}
	@RequestMapping(value = "add-food" , method=RequestMethod.POST )
    public String addFood(@ModelAttribute("food") @Valid FoodDTO food, Model model, 
    		BindingResult result, HttpServletRequest request, 
    		@RequestParam("file") MultipartFile file) 
	{
		List<FoodCategory> categories = fService.getAll();
		model.addAttribute("categories", categories);
		if (result.hasErrors()) {
        	model.addAttribute("reponsecode","0");
            return "admin/add-food";
        }
		String uploadRootPath = request.getServletContext().getRealPath("upload");
		String nameImage = file.getOriginalFilename();
		if(!nameImage.isEmpty()) { 
			food.setImage(nameImage);
			upload.UploadImage(file, uploadRootPath);
		}
		
		Food fo= new Food();
		FoodCategory category = fService.findByName(food.getCategory());
		Mapping.foodMap(fo, food);
		fo.setFoodCategory(category);
        rService.save(fo);
        model.addAttribute("reponsecode","1");
        model.addAttribute("food",food);
        return "admin/add-food";
    }
	@RequestMapping(value = "edit-food" , method=RequestMethod.POST )
    public String editFood(@ModelAttribute("food") @Valid FoodDTO food, Model model, 
    		BindingResult result, HttpServletRequest request, 
    		@RequestParam("file") MultipartFile file) 
	{
		List<FoodCategory> categories = fService.getAll();
		model.addAttribute("categories", categories);
		if (result.hasErrors()) {
        	model.addAttribute("reponsecode","0");
            return "admin/edit-food";
        }
		String uploadRootPath = request.getServletContext().getRealPath("upload");
		String nameImage = file.getOriginalFilename();
		if(!nameImage.isEmpty()) { 
			food.setImage(nameImage);
			upload.UploadImage(file, uploadRootPath);
		}
		
		Food fo= new Food();
		FoodCategory category = fService.findByName(food.getCategory());
		Mapping.foodMap(fo, food);
		fo.setFoodCategory(category);
        rService.save(fo);
        model.addAttribute("reponsecode","1");
        model.addAttribute("food",food);
        return "admin/edit-food";
    }
	@RequestMapping("edit-food/{id}")
	public String editFood(Model model, @PathVariable("id") int id)
	{
		List<FoodCategory> categories = fService.getAll();
		model.addAttribute("categories", categories);
		Food food = rService.getById(id);
		FoodDTO foDto = new FoodDTO();
		Mapping.foodMap(foDto, food);
		model.addAttribute("food", foDto);
		return "admin/edit-food";
	}
	@RequestMapping("delete-food/{id}")
	public String delFood(Model model, @PathVariable("id") int id, RedirectAttributes attributes)
	{
		try{
			rService.remove(id);
			attributes.addFlashAttribute("reponsecode", 1);
		}
		catch (Exception e) {
			attributes.addFlashAttribute("reponsecode", 0);
		}
		return "redirect:/admin/list-food";
	}
}
