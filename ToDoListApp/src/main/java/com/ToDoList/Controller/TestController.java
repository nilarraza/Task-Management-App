package com.ToDoList.Controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ToDoList.Model.User;

@CrossOrigin("http://localhost:4200")
@RestController
public class TestController {
	
	@GetMapping("test")
	public User test() {
		System.out.println("test work");
		User user=new User(1,"Nilar","preena","12345678","ncs");
		return user;
	}

}
