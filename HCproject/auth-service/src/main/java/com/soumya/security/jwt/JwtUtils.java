package com.soumya.security.jwt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import com.soumya.security.Service.UserDetailsImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.security.Key;

@Component
public class JwtUtils {

  @Value("${app.jwtSecret}")
  private String jwtSecret;

  @Value("${app.jwtExpirationMs}")
  private int jwtExpirationMs;
  
 public String generateJwtToken(Authentication authentication)
 {
   UserDetailsImpl userPrinciple=(UserDetailsImpl) authentication.getPrincipal();

   String role=userPrinciple.getAuthorities()
    .stream()
    .map(GrantedAuthority::getAuthority)
    .findFirst()
    .orElseThrow(()->new RuntimeException("Role not found"));

    System.out.println("Role: "+role);

   return Jwts.builder()
      .setSubject((userPrinciple.getUsername()))
      .claim("role", role)
      .setIssuedAt(new Date())
      .setExpiration(new Date((new Date()).getTime()+jwtExpirationMs))
      .signWith(key(), SignatureAlgorithm.HS512)
      .compact(); 
 }
  
 private Key key()
 {
  return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
 }

 public String getUserNameFromJwtToken(String token)
 {
   return Jwts.parserBuilder()
      .setSigningKey(key())
      .build()
      .parseClaimsJws(token)
      .getBody()
      .getSubject();
 }
  
 public boolean validateJwtToken(String authToken)
 {
   try {
     Jwts.parserBuilder()
        .setSigningKey(key())
        .build()
        .parseClaimsJws(authToken);
     return true;
   } catch (MalformedJwtException e) {
     System.out.println("Invalid JWT token: "+e.getMessage());
   }catch (ExpiredJwtException e) {
     System.out.println("Invalid JWT signature: "+e.getMessage());
   } catch (UnsupportedJwtException e) {
     System.out.println("JWT token is expired: "+e.getMessage());
   } catch (IllegalArgumentException e) {
     System.out.println("JWT claims string is empty: "+e.getMessage());
   }
   return false;
 }
}
