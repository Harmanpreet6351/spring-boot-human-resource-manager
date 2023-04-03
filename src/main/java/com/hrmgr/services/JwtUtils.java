package com.hrmgr.services;

import java.security.Key;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtUtils {
	static String SECRET_KEY = "2948404D635166546A576E5A7234743777217A25432A462D4A614E645267556B";
	
	public String getToken(String username) {
		return Jwts.builder()
				.setSubject(username)
				.signWith(getSignInKey())
				.compact();
	}
	
	public Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	public boolean verifyToken(String jwt, UserDetails user) {
		String username = extractUsername(jwt);
		return username.equals(user.getUsername());
	}
	
	public String extractUsername(String jwt) {
		String subject = Jwts.parserBuilder()
				.setSigningKey(getSignInKey())
				.build()
				.parseClaimsJws(jwt)
				.getBody().getSubject();
		return subject;
	}
}
