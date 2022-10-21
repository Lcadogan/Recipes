package com.example.presentation;

import com.example.businesslayer.User;
import com.example.businesslayer.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/api/register")
    public ResponseEntity<?> userRegistration(@Valid @RequestBody User user) {
       User obj = userService.findByEmail(user.getEmail());
        if (obj != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        user.setRole("ROLE_USER");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.userRegistration(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
