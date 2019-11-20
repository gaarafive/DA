package com.example.karaoke.dto;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

public class BillDTO {
	private int id;
	private String nameEmployee;
	private String nameCustomer;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date date;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date orderdate;
	private String sdate;
	private String startHour;
	private String endHour;
	private String totalHour;
	private int hourprice;
	private int foodprice;
	private int totalprice;
	private String status;
	private List<FoodDTO> foods;
	private List<String> foodid;
	private List<String> foodnumber;
	private String room;
	private String idroom;
	private String note;
	

	public Date getOrderdate() {
		return orderdate;
	}
	public void setOrderdate(Date orderdate) {
		this.orderdate = orderdate;
	}
	public String getIdroom() {
		return idroom;
	}
	public void setIdroom(String idroom) {
		this.idroom = idroom;
	}
	public BillDTO(int id, String nameEmployee) {
		super();
		this.id = id;
		this.nameEmployee = nameEmployee;
	}
	public String getTotalHour() {
		return totalHour;
	}
	public void setTotalHour(String totalHour) {
		this.totalHour = totalHour;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSdate() {
		return sdate;
	}
	public void setSdate(String sdate) {
		this.sdate = sdate;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	
	public List<String> getFoodnumber() {
		return foodnumber;
	}
	public void setFoodnumber(List<String> foodnumber) {
		this.foodnumber = foodnumber;
	}
	public List<String> getFoodid() {
		return foodid;
	}
	public void setFoodid(List<String> foodid) {
		this.foodid = foodid;
	}
	public List<FoodDTO> getFoods() {
		return foods;
	}
	public void setFoods(List<FoodDTO> foods) {
		this.foods = foods;
	}
	
	public BillDTO() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNameEmployee() {
		return nameEmployee;
	}
	public void setNameEmployee(String nameEmployee) {
		this.nameEmployee = nameEmployee;
	}
	public String getNameCustomer() {
		return nameCustomer;
	}
	public void setNameCustomer(String nameCustomer) {
		this.nameCustomer = nameCustomer;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getStartHour() {
		return startHour;
	}
	public void setStartHour(String startHour) {
		this.startHour = startHour;
	}
	public String getEndHour() {
		return endHour;
	}
	public void setEndHour(String endHour) {
		this.endHour = endHour;
	}
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
	public int getHourprice() {
		return hourprice;
	}
	public void setHourprice(int hourprice) {
		this.hourprice = hourprice;
	}
	public int getFoodprice() {
		return foodprice;
	}
	public void setFoodprice(int foodprice) {
		this.foodprice = foodprice;
	}
	public int getTotalprice() {
		return totalprice;
	}
	public void setTotalprice(int totalprice) {
		this.totalprice = totalprice;
	}
	
	
}
