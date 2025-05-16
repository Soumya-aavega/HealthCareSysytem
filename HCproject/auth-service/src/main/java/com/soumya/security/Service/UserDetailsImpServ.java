package com.soumya.security.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.soumya.model.User;
import com.soumya.repository.UserRepository;

import jakarta.transaction.Transactional;


@Service
public class UserDetailsImpServ implements UserDetailsService{

  @Autowired
  UserRepository userRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
   
    User user= userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("User not found with user name"+username));
    return UserDetailsImpl.build(user);
  }

}
