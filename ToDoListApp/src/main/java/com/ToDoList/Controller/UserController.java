package com.ToDoList.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ToDoList.Model.BlockedUser;
import com.ToDoList.Model.User;
import com.ToDoList.Repository.BlockedUserRepository;
import com.ToDoList.Repository.UserRepository;

@CrossOrigin
@RestController
@RequestMapping("user")
public class UserController {
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	BlockedUserRepository blockRepository;
	
	@GetMapping("users")
	public List<User> getUsers(){		
		return userRepository.findByRole("USER");		
	}
	
	@GetMapping("admins")
	public List<User> getAdmin(){		
		return userRepository.findByRole("ADMIN");		
	}

	@PostMapping("blockuser")
	public User block(@RequestBody int id){		
		System.out.println(id);
		User user=userRepository.findByuserid(id);
		BlockedUser buser=new BlockedUser(user.getUserid(),user.getUsername(),user.getEmail(),user.getPassword(),user.getRole());
		blockRepository.save(buser);
		userRepository.deleteById(id);		
		return user;		
	}
	
	@GetMapping("getblockeduser")
	public List<BlockedUser> getBlockUsers(){	
		return blockRepository.findAll();		
	}
	
	@PostMapping("unblockuser")
	public User unBlock(@RequestBody int id){		
		System.out.println(id);
		BlockedUser buser=blockRepository.findByuserid(id);
		User user=new User(buser.getUserid(),buser.getUsername(),buser.getEmail(),buser.getPassword(),buser.getRole());
		userRepository.save(user);
		blockRepository.deleteById(id);		
		return user;		
	}


}
