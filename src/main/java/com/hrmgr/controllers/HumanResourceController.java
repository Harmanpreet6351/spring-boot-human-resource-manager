package com.hrmgr.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hrmgr.models.User;
import com.hrmgr.repositories.UserRepository;
import com.hrmgr.requests.EmployeeRequest;
import com.hrmgr.responses.GenericMessage;

@RestController
public class HumanResourceController {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@PostMapping("/hr/employees/add")
	public ResponseEntity<GenericMessage> addEmployee(@RequestBody EmployeeRequest empReq) {
		Optional<User> user = userRepository.findByUsername(empReq.getUsername());
		if(!user.isEmpty()) {
			return new ResponseEntity<>(new GenericMessage("Username Already Exists"), HttpStatus.FORBIDDEN);
		}
		User newUser = new User();
		newUser.setEmail(empReq.getEmail());
		newUser.setPassword(passwordEncoder.encode(empReq.getPassword()));
		newUser.setUsername(empReq.getUsername());
		newUser.setRole("EMPL");
		userRepository.save(newUser);
		return new ResponseEntity<>(new GenericMessage("Employee Created"), HttpStatus.CREATED);
	}
	
	@GetMapping("/hr/employees")
	public ResponseEntity<List<User>> getAllEmployees() {
		return new ResponseEntity<>(userRepository.findByRole("EMPL"), HttpStatus.OK);
	}
	
	@GetMapping("/hr/employees/{username}")
	public ResponseEntity<User> getEmployee(@PathVariable String username) {
		Optional<User> user = userRepository.findByUsername(username);
		if(user.isEmpty()) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(user.get(), HttpStatus.OK);
	}
	
}
