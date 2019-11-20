package com.example.karaoke.controller;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.karaoke.Service.FoodService;
import com.example.karaoke.Service.PdfReportService;
import com.example.karaoke.Service.OrderService;
import com.example.karaoke.entity.Bill;
import com.example.karaoke.entity.Food;
import com.example.karaoke.entity.Order;
import com.example.karaoke.entity.OrderFood;



@Controller
public class PdfReportController {

    @Autowired
    private FoodService foodService;
    @Autowired
    private OrderService oService;
    @RequestMapping(value = "/pdfreport", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> citiesReport() {

        List<Food> cities = (List<Food>) foodService.getAll();

        ByteArrayInputStream bis = PdfReportService.citiesReport(cities);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=citiesreport.pdf");
        headers.setContentType(new MediaType("text", "plain", StandardCharsets.UTF_8));
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

    @Autowired
    private PdfReportService documentGeneratorService;

    @RequestMapping(value = "/employee/invoice/{idroom}", produces = "application/pdf")
    public ResponseEntity<InputStreamResource> html2pdf(@PathVariable int idroom) {
    	List<Order> od = oService.findOrderOfRoomWithStatusIsactive(false, idroom);
		Order order = new Order();
		order = od.get(0);
		SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm");
		if(order.getEndhour().isEmpty()) {
			order.setEndhour(hourFormat.format(new Date()));
			order.setTotalhour(totalHour(order.getStarthour(),order.getEndhour()));
		 
		}
		Bill bill = order.getBill();
		List<OrderFood> listOrderFoods = order.getOrderFoods();
		Map<String, Object> data = new HashMap<>();
		data.put("bill", bill);
		data.put("order", order);
		data.put("foods", listOrderFoods);
        InputStreamResource resource = documentGeneratorService.html2PdfGenerator(data);
        if (resource != null) {
            return ResponseEntity
                    .ok()
                    .body(resource);
        } else {
            return new ResponseEntity<InputStreamResource>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
    
    @RequestMapping(value = "/employee/print-invoice/{idroom}", produces = "application/pdf")
    public String printInvoice(@PathVariable int idroom, Model model) {
    	List<Order> od = oService.findOrderOfRoomWithStatusIsactive(false, idroom);
		Order order = new Order();
		order = od.get(0);
		SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm");
		if(order.getEndhour().isEmpty()) {
			order.setEndhour(hourFormat.format(new Date()));
			order.setTotalhour(totalHour(order.getStarthour(),order.getEndhour()));
		 
		}
		Bill bill = order.getBill();
		List<OrderFood> listOrderFoods = order.getOrderFoods();
		model.addAttribute("bill", bill);
		model.addAttribute("order", order);
		model.addAttribute("foods", listOrderFoods);
        return "employee/invoice-print";
    }
    public String totalHour(String startHour, String endHour) {
		int hours1 = Integer.parseInt((startHour.split(":")[0]));
        int minutes1 = Integer.parseInt((startHour.split(":")[1]));
        
        int hours2 = Integer.parseInt((endHour.split(":")[0]));
        int minutes2 = Integer.parseInt((endHour.split(":")[1]));
        
        int minutesstart = hours1*60+minutes1;
        int minutesend = hours2*60+minutes2;
        
        int minute = minutesend - minutesstart;
        if(minute<0) minute+=24*60;
        
        return minute+"";
	}
}