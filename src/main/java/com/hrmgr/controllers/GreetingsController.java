package com.hrmgr.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingsController {
	
	@GetMapping("/")
	Map<String, String> welcome() {
		Map<String, String> response = new HashMap<>();
		response.put("msg", "Welcome to Human Resource Management App");
		return response;
	}
	
}