package com.example.demo.auth;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;

@Component
public class JWTFilter extends OncePerRequestFilter {

    public static final String HEADER_STRING = "authorization";

    @Autowired
    private JWTToken jwtToken;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        Enumeration<String> headerNames = httpServletRequest.getHeaderNames();

        String header = httpServletRequest.getHeader(HEADER_STRING);

        String username = null;
        String authToken = null;

        if (header != null ) {

            authToken = header;

            try {
                username = jwtToken.getUserNameFromToken(authToken);
            } catch (IllegalArgumentException e) {

            } catch (ExpiredJwtException e) {

            }

        } else {
            System.out.println("Header bulunamadi");
        }

        // contextim current userin kimliği doğrulanmış mı ona bakar, getauth null ise kullanıcı yok demek
        if (username != null && authToken != null &&
                SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if(jwtToken.validate(authToken)){
                UsernamePasswordAuthenticationToken unameToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));

                unameToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

                System.out.println("Kullanıcı user" + username + "spring security contexine eklendi");

                /// Current user auth oldu kimliği doğrulandı ilerleyebilir
                SecurityContextHolder.getContext().setAuthentication(unameToken);
            }
        }

        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }

}
