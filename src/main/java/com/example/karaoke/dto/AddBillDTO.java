package com.example.karaoke.dto;

import java.util.List;

public class AddBillDTO {
	private BillDTO bill;
	private List<String> foodid;
	private List<String> foodnumber;
	
	public BillDTO getBill() {
		return bill;
	}
	public void setBill(BillDTO bill) {
		this.bill = bill;
	}
	public List<String> getFoodid() {
		return foodid;
	}
	public void setFoodid(List<String> foodid) {
		this.foodid = foodid;
	}
	public List<String> getFoodnumber() {
		return foodnumber;
	}
	public void setFoodnumber(List<String> foodnumber) {
		this.foodnumber = foodnumber;
	}
	
}
