package com.soumya.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties.Authentication;
import org.springframework.stereotype.Component;

import jakarta.validation.Valid;

// Import UserDetailsImpl if it exists in your project


@Component
public class JwtUtils {

  @Value("${app.jwtSecret}")
  private String jwtSecret;

  @Value("${app.jwtExpirationMs}")
  private int jwtExpirationMs;

}
