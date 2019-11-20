package com.example.karaoke.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
@Entity
@Table(name = "Users")
public class User {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@Column(unique = true)
	private String 	username;
	private String password;
	private String name;
	private String telephonenumber;
	private String address;
	@ManyToOne
    @JoinColumn(name="id_role", nullable=false)
	private Role role;
	
	@OneToMany(mappedBy="user")
	List<Order> orders;
	
	
	public User() {
	}
	public User(int id, String username, String password, String name, String telephonenumber, String address) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.name = name;
		this.telephonenumber = telephonenumber;
		this.address = address;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTelephonenumber() {
		return telephonenumber;
	}
	public void setTelephonenumber(String telephonenumber) {
		this.telephonenumber = telephonenumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	
	
}
