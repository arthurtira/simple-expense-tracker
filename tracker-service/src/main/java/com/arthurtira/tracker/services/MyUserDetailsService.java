package com.arthurtira.tracker.services;

import com.arthurtira.tracker.model.UserEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserEntityService userEntityService;

    public MyUserDetailsService(UserEntityService userEntityService) {
        this.userEntityService = userEntityService;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = this.userEntityService.findUserEntityByUsername(username);
        if(userEntity == null)
            throw new UsernameNotFoundException("User not found");

       return new User(userEntity.getUsername(), userEntity.getPassword(),  new ArrayList<>());
    }
}
