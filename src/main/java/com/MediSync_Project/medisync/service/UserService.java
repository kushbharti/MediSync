package com.MediSync_Project.medisync.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.MediSync_Project.medisync.model.User;
import com.MediSync_Project.medisync.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    final private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String register(User user) {
        // Check if username exists
        if (userRepository.findByUsername(user.getUsername()) != null) {
            return "Username already exists.";
        }

        // Password validation
        String password = user.getPassword();
        if (password.length() < 8 || !password.matches(".*[!@#$%^&*()].*")) {
            return "Password must be at least 8 characters and contain at least one special character.";
        }

        // Hash the password
        user.setPassword(passwordEncoder.encode(password));

        // Save user
        userRepository.save(user);

        return "User registered successfully.";
    }

    public String login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return "Invalid username or password.";
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            return "Invalid username or password.";
        }

        return "Login successful.";
    }
}
