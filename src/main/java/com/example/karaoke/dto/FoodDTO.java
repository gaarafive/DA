package com.example.karaoke.dto;

public class FoodDTO {
	private int id;
	private String name;
	private int price;
	private String number;
	private String image;
	private String category;
	//employee
	private int numberfood;
	private int idOrderfood;
	
	
	
	public int getIdOrderfood() {
		return idOrderfood;
	}
	public void setIdOrderfood(int idOrderfood) {
		this.idOrderfood = idOrderfood;
	}
	public int getNumberfood() {
		return numberfood;
	}
	public void setNumberfood(int numberfood) {
		this.numberfood = numberfood;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
}
