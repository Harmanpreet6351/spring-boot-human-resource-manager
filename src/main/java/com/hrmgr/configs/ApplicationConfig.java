package com.hrmgr.configs;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hrmgr.models.User;
import com.hrmgr.repositories.UserRepository;

@Configuration
public class ApplicationConfig {

	@Autowired
	UserRepository userRepo;
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationManager authManager(
				AuthenticationConfiguration config
			) throws Exception {
		return config.getAuthenticationManager();
	}
	
	@Bean
	UserDetailsService userDetailsService() {
		return new UserDetailsService() {

			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				Optional<User> user = userRepo.findByUsername(username);
				if(user.isEmpty())
					throw new UsernameNotFoundException("User Not Found");
				return user.get();
			}
			
		};
	}
	
	@Bean
	AuthenticationProvider authProvider() {
		DaoAuthenticationProvider authPrvdr = new DaoAuthenticationProvider();
		authPrvdr.setUserDetailsService(userDetailsService());
		authPrvdr.setPasswordEncoder(passwordEncoder());
		return authPrvdr;
	}
	
}
