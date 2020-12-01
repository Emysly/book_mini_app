package com.emysilva.demo.service;

import com.emysilva.demo.model.User;
import com.emysilva.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);

        user.orElseThrow(()-> new UsernameNotFoundException(username + "not found"));

        MyUserDetails userDetails = new MyUserDetails();
        userDetails.setUser(user.get());

        return userDetails;
    }

}