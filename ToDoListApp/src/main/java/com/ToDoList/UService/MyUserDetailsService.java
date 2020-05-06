package com.ToDoList.UService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ToDoList.Model.User;
import com.ToDoList.Repository.UserRepository;


@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
      

        User user= userRepository.findByUsername(userName);
		MyUserDetails userDetails=null;
		if(user!=null) {
			userDetails=new MyUserDetails(user);
			
		}else {
			throw new UsernameNotFoundException("user not exist with name"+userName);
		}
		
		return userDetails;
	}

}
  
