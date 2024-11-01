package com.example.salesapp.service;

import com.example.salesapp.model.User;
import com.example.salesapp.repository.UserRepository;
import com.example.salesapp.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Encrypt password
        user.setActive(false); // Default to inactive
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);
        return "User registered, awaiting activation.";
    }

    public String activateUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setActive(true);
        userRepository.save(user);
        return "User activated.";
    }

    public String login(String username, String password) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        if (!user.isActive()) {
            throw new RuntimeException("User not activated.");
        }
        if (passwordEncoder.matches(password, user.getPassword())) {
            return jwtUtil.generateToken(user);
        } else {
            throw new RuntimeException("Invalid credentials.");
        }
    }
}
