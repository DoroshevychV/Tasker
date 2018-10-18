package com.tasker.security;

import com.tasker.entity.Person;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenUtils {

	@Value("${lgs.token.secret}")
	private String secret;
	
	@Value("${token.expired}")
	private Long expiredTime;

	public String getUsernameFromToken(String token) {
		String username;
		try {
			final Claims claims = this.getClaimsFromToken(token);
			username = (String) claims.get("username");
		} catch (Exception e) {
			username = null;
		}
		return username;
	}


	public Date getExpirationDate(String token) {
		Date createdAt;
		try {
			final Claims claims = this.getClaimsFromToken(token);
			createdAt = new Date((Long) claims.get("createdAt"));
		} catch (Exception e) {
			throw new IllegalArgumentException("Don`t get createdAt");
		}
		return createdAt;
	}


	private Claims getClaimsFromToken(String token) {
		Claims claims;
		try {
			claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			claims = null;
		}
		return claims;
	}

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<String, Object>();
		claims.put("username", userDetails.getUsername());
		claims.put("createdAt", new Date())	;
		return this.generateToken(claims);
	}

	private String generateToken(Map<String, Object> claims) {
		return Jwts.builder().setClaims(claims)
				.signWith(SignatureAlgorithm.HS512, this.secret).compact();
	}



	public Boolean validateToken(String token, UserDetails userDetails) {
		Person person = (Person) userDetails;
		final String username = this.getUsernameFromToken(token);
		final Date createdAt = this.getExpirationDate(token);
		boolean expired = false;
		Date now = new Date();
		if((now.getTime()-createdAt.getTime())<expiredTime){
			expired = true;
		}
		return (username.equals(person.getEmail()) && expired);
	}
}
