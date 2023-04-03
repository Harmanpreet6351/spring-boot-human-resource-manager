package com.hrmgr.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hrmgr.models.User;
import com.hrmgr.repositories.UserRepository;
import com.hrmgr.requests.CredentialRequest;
import com.hrmgr.responses.JwtResponse;
import com.hrmgr.services.JwtUtils;

@RestController
public class AuthController {

	@Autowired
	JwtUtils jwtUtils;
	
	@Autowired
	AuthenticationManager authManager;
	
	@Autowired
	PasswordEncoder passEncoder;
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/auth/token")
	public ResponseEntity<JwtResponse> getToken(@RequestBody CredentialRequest creds) {
		Optional<User> user = userRepository.findByUsername(creds.getUsername());
		if(user.isEmpty()) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		try {
			authManager.authenticate(
					new UsernamePasswordAuthenticationToken(creds.getUsername(), creds.getPassword())
					);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		JwtResponse response = new JwtResponse(jwtUtils.getToken(user.get().getUsername()));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
}
