package com.soumya.security.jwt;

import java.io.IOException;

import java.util.logging.Logger;
import org.springframework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;


import com.soumya.security.Service.UserDetailsImpServ;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthTokenFilter extends OncePerRequestFilter{

  @Autowired
  private JwtUtils jwtUtils;

  @Autowired
  private UserDetailsImpServ userDetailsImpServ;

  private static final Logger logger = Logger.getLogger(AuthTokenFilter.class.getName());
  

 @Override
 protected void doFilterInternal(
     @org.springframework.lang.NonNull HttpServletRequest request,
     @org.springframework.lang.NonNull HttpServletResponse response,
     @org.springframework.lang.NonNull FilterChain filterChain)
     throws ServletException, IOException {
    try {
      String jwt = parseJwt(request);
      if (jwt!=null && jwtUtils.validateJwtToken(jwt)) {
        String username= jwtUtils.getUserNameFromJwtToken(jwt);
        UserDetails userDetails= userDetailsImpServ.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
            userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(userDetails);
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    } catch( Exception e) {
      logger.log(java.util.logging.Level.SEVERE, "Cannot set user authentication", e);
    }
    filterChain.doFilter(request, response);

 
}



 private String parseJwt(HttpServletRequest request) {

  String headerAuth = request.getHeader("Authorization");
 
  if(StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer "))
  {
    return headerAuth.substring(7);
 
  }
  return null;
 }
}
