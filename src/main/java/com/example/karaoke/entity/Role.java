package com.example.karaoke.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
@Entity
@Table(name = "Roles")
public class Role {
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@Column(unique=true)
	private String name;
	@OneToMany(mappedBy="role")
	List<User> users;
	
	public Role() {
	}
	public Role(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public Role(int id, String name, List<User> users) {
		super();
		this.id = id;
		this.name = name;
		this.users = users;
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
	public List<User> getAccounts() {
		return users;
	}
	public void setAccounts(List<User> accounts) {
		this.users = accounts;
	}
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	
	
}
