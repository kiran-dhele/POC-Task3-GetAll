package com.neosoft.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



import com.neosoft.main.model.Student;
import com.neosoft.main.model.UserRequest;
import com.neosoft.main.model.UserResponse;
import com.neosoft.main.security.JwtUtil;
import com.neosoft.main.serviceImpl.ServiceImpl;
import com.neosoft.main.serviceInterf.ServiceInterf;

@RestController
public class HomeController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private ServiceImpl ServiceImpl;
	
	@Autowired
	ServiceInterf serviceInterf;
	
	@Autowired
	private JwtUtil jwtUtilToken;
	
//This URL is used to get all student. It can access only through provided jwt token.
	@GetMapping("/getAllStudent")
	public List<Student> getAllStudent()
	{
		List<Student> list=serviceInterf.getAllStudent();
		return list;
	}

//This URL is used to get jwt token. username and password must be admin and admin.	
	@PostMapping("/authenticate")
	public ResponseEntity<?> loginJWTToken(@RequestBody UserRequest userRequest) throws Exception
	{
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword()));
			
		} catch (BadCredentialsException e) {
			// TODO: handle exception
			throw new Exception("Incorrect username and password");
		}
		
		final UserDetails userDetail=ServiceImpl.loadUserByUsername(userRequest.getUsername());
		
		final String jwt=jwtUtilToken.generateToken(userDetail);
		return ResponseEntity.ok(new UserResponse(jwt));
	}
	
}
