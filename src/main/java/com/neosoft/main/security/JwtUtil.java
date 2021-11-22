package com.neosoft.main.security;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.config.web.server.ServerHttpSecurity.OAuth2ResourceServerSpec.JwtSpec;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtil {
	
//	private String secret;
//	private int jwtExpirationInMs;
//
//	//@Value("${jwt.secret}")
//	public void setSecret(String secret) {
//		this.secret = secret;
//	}
//	
//	//@Value("${jwt.expirationDateInMs}")
//	public void setJwtExpirationInMs(int jwtExpirationInMs) {
//		this.jwtExpirationInMs = jwtExpirationInMs;
//	}
//
//	// generate token for user
//	public String generateToken(UserDetails userDetails) {
//		Map<String, Object> claims = new HashMap<>();
//		Collection<? extends GrantedAuthority> roles = userDetails.getAuthorities();
//		if (roles.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
//			claims.put("isAdmin", true);
//		}
//		if (roles.contains(new SimpleGrantedAuthority("ROLE_USER"))) {
//			claims.put("isUser", true);
//		}
//		return doGenerateToken(claims, userDetails.getUsername());
//	}
//
//	private String doGenerateToken(Map<String, Object> claims, String subject) {
//
//		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
//				.setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs)).signWith(SignatureAlgorithm.HS512, secret).compact();
//	}

	private String key="abcd";
	
	public String extractUsername(String token)
	{
		return extractClaim(token, Claims::getSubject);
	}
	
	public Date extractExpiration(String token)
	{
	   return extractClaim(token, Claims::getExpiration);	
	}
	
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver)
	{
		final Claims claims=extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	private Claims extractAllClaims(String token)
	{
		return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
	}
	
	private boolean isTokenExpired(String token)
	{
		return extractExpiration(token).before(new Date());
	}
	
	public String generateToken(UserDetails userDetails)
	{
		Map<String, Object> claims=new HashMap<String, Object>();
		return createToken(claims, userDetails.getUsername());
	}
	
	public String createToken(Map<String, Object> claims, String subject)
	{
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000*60*60*10))
				.signWith(SignatureAlgorithm.HS256, key).compact();
	}
	
	public boolean validateToken(String token, UserDetails userDetails)
	{
		final String username=extractUsername(token);
		return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
	}
}
