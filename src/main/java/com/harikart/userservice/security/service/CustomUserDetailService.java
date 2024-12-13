package com.harikart.userservice.security.service;

import com.harikart.userservice.model.User;
import com.harikart.userservice.repositiry.UserRepository;
import com.harikart.userservice.security.model.CustomUserDetails;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private UserRepository  userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByEmail(username);

        if(userOptional.isEmpty()){
            throw new UsernameNotFoundException("User by email id: "+ username+" doesnt exist.");
        }

        CustomUserDetails customUserDetails= new CustomUserDetails(userOptional);
        return customUserDetails;
    }
}
