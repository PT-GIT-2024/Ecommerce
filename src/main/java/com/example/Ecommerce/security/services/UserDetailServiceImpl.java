package com.example.Ecommerce.security.services;

import com.example.Ecommerce.model.User;
import com.example.Ecommerce.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    //we get User Entity from DB then we cast it to UserDetail
    //Then it becomes usable for JWT classes

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       User user = userRepository.findByUserName(username)
               .orElseThrow(()-> new UsernameNotFoundException("User Not found with username: " + username));

       return UserDetailsImpl.build(user);
    }
}
