package com.arthurtira.tracker.services.impl;

import com.arthurtira.tracker.services.AuthService;
import com.arthurtira.tracker.services.MyUserDetailsService;
import com.arthurtira.tracker.util.JwtUtil;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final MyUserDetailsService myUserDetailsService;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(MyUserDetailsService userDetailsService, JwtUtil jwtUtil) {
        this.myUserDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }
    @Override
    public String login(String username) {
        UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);
        return jwtUtil.generateToken(userDetails);
    }
}
