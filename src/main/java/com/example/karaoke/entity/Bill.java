package com.example.karaoke.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Bills")
public class Bill {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private int totalprice;
	private int hourprice;
	private int foodprice;
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER, mappedBy = "bill")
	private Order order;
	private boolean status;
	
	

	public Bill() {
	}
	
	public Bill(int id, int totalprice, int hourprice, int foodprice, Date date) {
		super();
		this.id = id;
		this.totalprice = totalprice;
		this.hourprice = hourprice;
		this.foodprice = foodprice;
		this.date = date;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTotalprice() {
		return totalprice;
	}
	public void setTotalprice(int totalprice) {
		this.totalprice = totalprice;
	}
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
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
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
}
