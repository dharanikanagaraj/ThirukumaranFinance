package com.java.finance.ThirukumaranFinance.Controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.finance.ThirukumaranFinance.Domain.LoginRequest;
import com.java.finance.ThirukumaranFinance.Service.LoginService;

import jakarta.servlet.ServletException;
import lombok.RequiredArgsConstructor;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping("/api/auth/")
@RequiredArgsConstructor
public class LoginController {

	// Defining expiry time of the JWTtoken in milliseconds
	long expireTime = 1200000;

	private final LoginService loginService;

	@PostMapping("login/lineboy")
	public ResponseEntity<?> loginUser(@RequestBody LoginRequest user) {

		String jwtToken = "";
		Map<String,String> map = new HashMap<>();		
		
		try {
			
			// Calling the getToken method as written below
			jwtToken = getTokenForLineBoy(user.getPhoneNo(),user.getPassword());
			map.clear();
			map.put("message", "User logged in successfully");
			map.put("Token",jwtToken);
			
		}catch(Exception e) {
			
			// In case of exception returning the error message and the null in place of token
			map.clear();
			map.put("message",e.getMessage());
			map.put("Token",null);
		}
		return new ResponseEntity<>(map,HttpStatus.OK);
	}
	
	
	
	public String getTokenForLineBoy(String username,String password) throws Exception {
		
		// If either of username or password field is empty it will throw exception
		if(username == null || password == null) {
			throw new ServletException("Please fill the Username and Password");
		}
		
		// Calling the validate(String username, String password) of return type boolean
		boolean status = loginService.validate(username,password);
		
		// Throwing exception if the user is not a valid user
		if(!status) {
			throw new ServletException("Invalid Credentials");
		}
		
		// Generating token only when the user is validated
		String jwtToken = Jwts.builder().setSubject(username).setIssuedAt(new Date())
							.signWith(SignatureAlgorithm.HS256, "CplayerAppkey").compact();
		return jwtToken;
	}
	
	@PostMapping("login/admin")
	public ResponseEntity<?> loginUserAdmin(@RequestBody LoginRequest user) {

		String jwtToken = "";
		Map<String,String> map = new HashMap<>();		
		
		try {
			
			// Calling the getToken method as written below
			jwtToken = getTokenForAdmin(user.getPhoneNo(),user.getPassword());
			map.clear();
			map.put("message", "User logged in successfully");
			map.put("Token",jwtToken);
			
		}catch(Exception e) {
			
			// In case of exception returning the error message and the null in place of token
			map.clear();
			map.put("message",e.getMessage());
			map.put("Token",null);
		}
		return new ResponseEntity<>(map,HttpStatus.OK);
	}
	
	
	
	public String getTokenForAdmin(String username,String password) throws Exception {
		
		// If either of username or password field is empty it will throw exception
		if(username == null || password == null) {
			throw new ServletException("Please fill the Username and Password");
		}
		
		// Calling the validate(String username, String password) of return type boolean
		boolean status = loginService.validateAdmin(username,password);
		
		// Throwing exception if the user is not a valid user
		if(!status) {
			throw new ServletException("Invalid Credentials");
		}
		
		// Generating token only when the user is validated
		String jwtToken = Jwts.builder().setSubject(username).setIssuedAt(new Date())
							.setExpiration(new Date(System.currentTimeMillis()+ expireTime))
							.signWith(SignatureAlgorithm.HS256, "CplayerAppkey").compact();
		return jwtToken;
	}
}
