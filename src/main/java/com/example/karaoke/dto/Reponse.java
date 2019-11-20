package com.example.karaoke.dto;

import java.util.List;

public class Reponse<T> {
	public int code;
	public String message;
	public List<T> result;
	
	public Reponse(int code, String message, List<T> result) {
		this.code = code;
		this.message = message;
		this.result = result;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<T> getResult() {
		return result;
	}
	public void setResult(List<T> result) {
		this.result = result;
	}
	
	
}
