package com.example.security.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserDetailService implements UserDetailsService {

    private final static Map<String,String> users = new HashMap<>();

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostConstruct
    public void createUsers(){
        users.put("tuncays",bCryptPasswordEncoder.encode("123"));
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        if(users.containsKey(s)){
            return new User(s,users.get(s), List.of(new Role("USER")));
        }
        throw new UsernameNotFoundException(s);

    }
}
