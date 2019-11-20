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

import com.example.karaoke.Service.RoomService;
import com.example.karaoke.entity.Room;

@Controller
@RequestMapping("/admin/")
public class RoomController {
	@Autowired
	RoomService rService;
	@RequestMapping("list-room")
	public String listUser(Authentication authentication, Model model)
	{
		List<Room> rooms = rService.getAll();
		model.addAttribute("rooms", rooms);
		return "admin/list-rooms";
	}
	@RequestMapping("add-room")
	public String addRoom(Model model)
	{
		Room room = new Room();
		model.addAttribute("room", room);
		return "admin/add-room";
	}
	@RequestMapping(value = "add-room" , method=RequestMethod.POST )
    public String addRoom(@ModelAttribute("room") @Valid Room room, Model model)
	{
        rService.save(room);
        model.addAttribute("reponsecode","1");
        model.addAttribute("room",room);
        return "admin/add-room";
    }
	@RequestMapping(value = "edit-room" , method=RequestMethod.POST )
    public String editRoom(@ModelAttribute("room") @Valid Room room, Model model)
	{
        rService.save(room);
        model.addAttribute("reponsecode","1");
        model.addAttribute("room",room);
        return "admin/edit-room";
    }
	@RequestMapping("edit-room/{id}")
	public String editRoom(Model model, @PathVariable("id") int id)
	{
		Room room = rService.getById(id);
		model.addAttribute("room", room);
		return "admin/edit-room";
	}
	@RequestMapping("delete-room/{id}")
	public String delUser(Model model, @PathVariable("id") int id, RedirectAttributes attributes)
	{
		try{
			rService.remove(id);
			attributes.addFlashAttribute("reponsecode", 1);
		}
		catch (Exception e) {
			attributes.addFlashAttribute("reponsecode", 0);
		}
		return "redirect:/admin/list-room";
	}
}
