package com.ToDoList.Controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ToDoList.Model.AuthenticationRequest;
import com.ToDoList.Model.AuthenticationResponse;
import com.ToDoList.Model.ResetToken;
import com.ToDoList.Model.User;
import com.ToDoList.Repository.ResetTokenRepository;
import com.ToDoList.Repository.UserRepository;
import com.ToDoList.UService.MyUserDetailsService;
import com.ToDoList.UService.TaskService;
import com.ToDoList.Util.EmailUtil;
import com.ToDoList.Util.JwtUtil;

@CrossOrigin
@RestController
@EnableScheduling
@RequestMapping("auth")
public class AuthController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	TaskService taskService;

	@Autowired
	private ResetTokenRepository resetTokenRepository;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtTokenUtil;

	@Autowired
	private EmailUtil email;

	@Autowired
	private MyUserDetailsService userDetailsService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@PostMapping("/signUp")
	public User saveUser(@RequestBody User user) {
		user.setRole("USER");
		String pass = user.getPassword();
		String encpass = passwordEncoder.encode(pass);
		user.setPassword(encpass);
		userRepository.save(user);
		return user;

	}

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
			throws Exception {

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		} catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

		final String jwt = jwtTokenUtil.generateToken(userDetails);
		final UserDetails user = userDetails;

		return ResponseEntity.ok(new AuthenticationResponse(jwt, user));
	}

	@PostMapping("/findme")
	public ResponseEntity<?> findMe(@RequestBody String username) {
		if (userRepository.findByUsername(username) == null) {
			System.out.println(userRepository.findByUsername(username));
			return (ResponseEntity<?>) ResponseEntity.badRequest();
		} else {
			System.out.println(userRepository.findByUsername(username));

			User user = userRepository.findByUsername(username);
			int userId = user.getUserid();
			String resetToken = UUID.randomUUID().toString();
			ResetToken token = new ResetToken();
			token.setUserId(userId);
			token.setToken(resetToken);
			resetTokenRepository.save(token);
			email.sendEmail(user.getEmail(), "Reset Password Token Verification",
					"http://localhost:8080/app/tokenvalidate?token=" + resetToken);

			return ResponseEntity.ok(userRepository.findByUsername(username));
		}
	}

	@RequestMapping("/tokenvalidate")
	public String passwordTokenValidation(String token) {
		// System.out.println(token);
		ResetToken resetToken = resetTokenRepository.findByToken(token);
		String token1 = resetToken.getToken();

		if (token1 == null) {
			return "Password reset token is invalid.";
		} else {
			int userId = resetToken.getUserId();
			User user = userRepository.findByuserid(userId);
			email.sendEmail(user.getEmail(), "Password Reset Verification Success",
					"You are passed the token verification. Now you can reset your password through the link provided below."
							+ " http://localhost:4200/auth/resetpassword");

			return "Password reset mail sent to your account successfully.";
		}

	}

	@PostMapping("/resetpassword")
	public User resetPassword(@RequestBody String password) {
		java.util.List<ResetToken> token = resetTokenRepository.findAll();
		ResetToken tokenOBJ = token.get(0);
		int id = tokenOBJ.getUserId();
		User user = userRepository.findByuserid(id);
		System.out.println(user.getUsername());
		user.setPassword(passwordEncoder.encode(password));
		userRepository.save(user);
		resetTokenRepository.deleteAll();
		return user;

	}

	
}
