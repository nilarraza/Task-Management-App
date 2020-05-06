package com.ToDoList.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ResetToken {
	
	@Id
	@GeneratedValue
	private int id;
	private int userId;
	private String token;
	
	
	
	public ResetToken() {
		super();
	}
	public ResetToken(int id, int userId, String token) {
		super();
		this.id = id;
		this.userId = userId;
		this.token = token;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	

}
