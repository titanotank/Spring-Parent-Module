package com.example.demo.api;

import com.example.demo.auth.JWTToken;
import com.example.demo.auth.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Message {


    // Login icin authentication manager lazım
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private JWTToken jwtToken;

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

        //Kinlik doğrulama
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginForm.getUsername(),loginForm.getPassword()));

        //Kimlik doğru ise token yaratma
        String token = jwtToken.generateToke(loginForm.getUsername());

        System.out.println("Token oluşturuldu : " + token);
        return ResponseEntity.ok(token);
    }

}
