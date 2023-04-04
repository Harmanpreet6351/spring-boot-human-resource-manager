package com.hrmgr.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hrmgr.requests.SalaryRequest;
import com.hrmgr.responses.GenericMessage;
import com.hrmgr.models.Salary;
import com.hrmgr.models.User;
import com.hrmgr.repositories.SalaryRepository;
import com.hrmgr.repositories.UserRepository;

@RestController
public class SalaryController {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	SalaryRepository salaryRepository;
	
	@PostMapping("/salary/add")
	public ResponseEntity<GenericMessage> paySalary(@RequestBody SalaryRequest salReq) {
		Optional<User> user = userRepository.findByUsername(salReq.getUsername());
		if(user.isEmpty()) {
			return new ResponseEntity<>(new GenericMessage("Employee does not exists"), HttpStatus.NOT_FOUND);
		}
		Salary salary = new Salary();
		salary.setAmount(Integer.parseInt(salReq.getAmount()));
		salary.setUser(user.get());
		salary.setPaidOn(new java.util.Date());
		salaryRepository.save(salary);
		return new ResponseEntity<>(new GenericMessage("Salary Paid"), HttpStatus.CREATED);
	}
	
	@GetMapping("/salary/{username}")
	public ResponseEntity<Object> getEmployeeSalary(@PathVariable String username) {
		Optional<User> user = userRepository.findByUsername(username);
		if(user.isEmpty()) {
			return new ResponseEntity<>(new GenericMessage("Employee does not exists"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(user.get().getSalaries(), HttpStatus.NOT_FOUND);
	}
	
}
