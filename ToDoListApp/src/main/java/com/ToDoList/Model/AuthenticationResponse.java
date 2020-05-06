package com.ToDoList.Model;

import java.io.Serializable;

import org.springframework.security.core.userdetails.UserDetails;

public class AuthenticationResponse implements Serializable {

    private final String jwt;
    private final UserDetails user;

  

    public AuthenticationResponse(String jwt, UserDetails user) {
		super();
		this.jwt = jwt;
		this.user = user;
	}



	public String getJwt() {
        return jwt;
    }



	public UserDetails getUser() {
		return user;
	}
	
	
}
