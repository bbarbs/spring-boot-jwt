package com.auth.feature.web;

import com.auth.feature.model.User;
import com.auth.feature.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Inject
    BCryptPasswordEncoder passwordEncoder;

    @Inject
    UserRepository userRepository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        // Encrypt the user password.
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        // Save user.
        this.userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUserById(@PathVariable int id) {
        return this.userRepository.findOne(Long.valueOf(id));
    }
}