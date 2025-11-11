package com.example.jwt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	//application.properties에 설정된 값
	@Value("${app.jwt.secret}")
	private String secret;
	
	@Value("${app.jwt.issuer}")
	private String issuer;
	
	@Value("${app.jwt.valid-seconds}")
	private long validSeconds;
	
	// JWT 서명에 사용할 비밀키(SecretKey)를 생성
	private Key key() {
		return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
	}
	
	// 토큰 생성
	public String create(String username) {
		long now = System.currentTimeMillis();
		return Jwts.builder().setSubject(username).setIssuer(issuer)
				.setIssuedAt(new Date(now)).setExpiration(new Date(now + validSeconds * 1000))
				.signWith(key(), SignatureAlgorithm.HS256).compact();
	}
	
	// 토큰을 분석하고 검증
	public Jws<Claims> parse(String token) {
		return Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token);
	}
}