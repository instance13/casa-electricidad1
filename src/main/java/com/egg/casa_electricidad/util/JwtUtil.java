package com.egg.casa_electricidad.util;

import java.security.Key;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.egg.casa_electricidad.enumeraciones.Rol;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service // this just provides semantic meaning
public class JwtUtil {
  @Value("${jwt.secret}")
  private String secretKey;
  @Value("${jwt.expiration}")
  private long expirationTime;

  private Key getSigningKey() {
    return Keys.hmacShaKeyFor(secretKey.getBytes()); // create a secure key for HMAC-SHA256
  }

  public String generateToken(String username, Collection<? extends GrantedAuthority> authorities) {
        List<String> roles = authorities.stream()
        .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());
        
    return Jwts.builder()
        .setSubject(username)
        .claim("role", roles)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
        .signWith(getSigningKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
      return true;
    } catch (JwtException e) {
      return false;
    }
  }

  public Claims extractAllClaims(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(getSigningKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  public String extractUsername(String token) {
    return extractClaims(token, Claims::getSubject);
  }

  public List<Rol> extractRoles(String token) {
    List<?> rawRoles = extractClaims(token, claims -> claims.get("roles", List.class)); // this method does not necessarily return String

    if (rawRoles == null)
      return Collections.emptyList(); 

    return rawRoles.stream()
        .filter(role -> role instanceof String) // ensure it's actually a string
        .map(role -> Rol.valueOf((String) role)) // safe cast
        .collect(Collectors.toList());
  }

}
