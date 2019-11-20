package com.example.karaoke.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.example.karaoke.formvalidate.FieldMatch;


@FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match")
public class UserDTO {
	private int id;
	@Size(min=1, max=32, message="First name must be between 1 and 32 characters")
	@NotEmpty(message="Please enter your name")
    private String name;
	@NotEmpty(message="Please enter your username")
    private String 	username;
    private String password;
    private String confirmPassword;
	
	private String oldpassword;
	private String telephonenumber;
	private String address;
	
	
	
	public String getOldpassword() {
		return oldpassword;
	}
	public void setOldpassword(String oldpassword) {
		this.oldpassword = oldpassword;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@NotEmpty(message="Please select a role")
	private String role;
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
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
	public void setRole(String role) {
		this.role = role;
	}
	public String getRole() {
		return role;
	}
    
    
}