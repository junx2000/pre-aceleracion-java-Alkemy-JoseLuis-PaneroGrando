package com.alkemy.disney.auth.service;

import com.alkemy.disney.auth.dto.UserDTO;
import com.alkemy.disney.auth.entity.UserEntity;
import com.alkemy.disney.auth.repository.UserRepository;
import com.alkemy.disney.exception.UserAlreadyExistAuthException;
import com.alkemy.disney.service.EmailService;
import org.apache.tomcat.util.buf.UEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsCustomService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private EmailService emailService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(userName);
        if (userEntity == null) {
            throw new UsernameNotFoundException("Username or password not found");
        }
        return new User(userEntity.getUsername(), userEntity.getPassword(), Collections.emptyList());
    }

    public boolean save(UserDTO userDTO) throws UserAlreadyExistAuthException {
        UserEntity user = userRepository.findByUsername(userDTO.getUsername());
        if (user != null) {
            throw new UserAlreadyExistAuthException("The username already exists");
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userDTO.getUsername());
        userEntity.setPassword(encoder.encode(userDTO.getPassword()));
        userEntity = userRepository.save(userEntity);
        if (userEntity != null) {
            emailService.sendWelcomeEmailTo(userEntity.getUsername());
        }
        return userEntity != null;
    }
}
