package com.example.security.api;

import com.example.security.auth.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Message {

    // Login icin authentication manager lazım
    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/all")
    public ResponseEntity<String> all(){
        return ResponseEntity.ok("Herkese acık");
    }

    @GetMapping("/message")
    public ResponseEntity<String> message(){
        return ResponseEntity.ok("Giriş onaylandı");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginForm loginForm){

        return ResponseEntity.ok("BASARILI");
    }

}
