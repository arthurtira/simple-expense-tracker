package com.arthurtira.tracker.filters;

import com.arthurtira.tracker.services.MyUserDetailsService;
import com.arthurtira.tracker.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    private final MyUserDetailsService myUserDetailsService;
    private final JwtUtil jwtUtil;

    public JwtRequestFilter(MyUserDetailsService myUserDetailsService, JwtUtil jwtUtil) {
        this.myUserDetailsService = myUserDetailsService;
        this.jwtUtil = jwtUtil;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        String path = httpServletRequest.getRequestURI();
        log.debug("URI {} " + path);
        if (path.contains("auth") || path.contains("/api-docs") || path.contains("swagger")) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        final String header = httpServletRequest.getHeader("Authorization");
        log.debug("Intercepted a request {} " + header);
        if(header == null) {
            throw new RuntimeException("No authorization token");
        }

        String username = null;
        String jwt = null;

        if(header != null && header.startsWith("Bearer ")) {
            jwt = header.substring(7);
            username = jwtUtil.extractUsername(jwt);
        }

        log.debug(" Username {} " + username);

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.myUserDetailsService.loadUserByUsername(username);
            log.debug("User details {} " + userDetails);
            if(jwtUtil.validateToken(jwt, userDetails)) {
                log.debug("Token is valid {} ");
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().
                        buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken );
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
