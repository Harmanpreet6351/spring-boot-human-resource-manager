package com.hrmgr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hrmgr.models.User;
import com.hrmgr.repositories.UserRepository;

@SpringBootApplication
public class HumanResourceManagerApplication implements ApplicationRunner {
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(HumanResourceManagerApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		User user = new User();
		user.setUsername("admin123");
		user.setEmail("admin@xyz.com");
		user.setPassword(passwordEncoder.encode("admin"));
		user.setRole("HR");
		userRepository.save(user);
	}

}
