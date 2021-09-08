package com.example.airbus.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.airbus.config.JwtTokenUtil;
import com.example.airbus.dto.JwtRequest;
import com.example.airbus.dto.User;
import com.example.airbus.repository.UserRepository;
import com.example.airbus.service.JwtUserDetailsService;
import com.example.airbus.service.UserService;


@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@Autowired
	UserRepository userRepository;

	@RequestMapping({ "/hello" })
	public String firstPage() {
		return "Hello World";
	}

	@GetMapping("/list")
	public List<User> getListOfUsers() {
		System.out.println("Inside Users controller");
		return userService.listUsers();
	}

	@DeleteMapping("/delete/{userId}")
	public void deleteUser(@PathVariable Integer userId) {
		userService.deleteUser(userId);
	}

	@PostMapping("/signup")
	public User createUser(@RequestBody User user) {
		return userService.createUser(user);
	}

	@PostMapping("/updateUser")
	public User updateUser(@RequestBody User user) {
		return userService.updateUser(user);
	}

	@GetMapping("/getUserById/{userId}")
	public User getUserById(@PathVariable Integer userId) {
		return userService.getUserById(userId);
	}

	@PostMapping("/login")
	public ResponseEntity<User> login(@RequestBody JwtRequest authenticationRequest, HttpServletRequest req, Authentication authentication) {
		System.out.println("inside loign");
		User loginUser = userService.findUserByEmail(authenticationRequest.getUsername());

		if (loginUser == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword().trim()));
		} catch (Exception e) {
			e.printStackTrace();

		}
		HttpHeaders headers = new HttpHeaders();
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		String token = jwtTokenUtil.generateToken(userDetails);
		headers.add("token", token);
		loginUser.setToken(token);

		// store User Agent data
		return new ResponseEntity<>(loginUser, headers, HttpStatus.OK);
	}

}
