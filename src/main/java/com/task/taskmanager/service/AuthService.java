// package com.task.taskmanager.service;

// import com.task.taskmanager.model.*;
// import com.task.taskmanager.repository.UserRepository;
// import com.task.taskmanager.security.JwtService;
// import lombok.RequiredArgsConstructor;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Service;

// @Service
// @RequiredArgsConstructor
// public class AuthService {

//     private final UserRepository userRepository;
//     private final PasswordEncoder passwordEncoder;
//     private final JwtService jwtService;

//     public String register(RegisterRequest request) {

//         User user = User.builder()
//                 .name(request.getName())
//                 .email(request.getEmail())
//                 .password(passwordEncoder.encode(request.getPassword()))
//                 .role(Role.USER)
//                 .build();

//         userRepository.save(user);

//         return "User Registered Successfully";
//     }

//     public String login(LoginRequest request) {

//         User user = userRepository.findByEmail(request.getEmail())
//                 .orElseThrow(() -> new RuntimeException("User not found"));

//         if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
//             throw new RuntimeException("Invalid credentials");
//         }

//         return jwtService.generateToken(user.getEmail(), user.getRole().name());
//     }
// }


package com.task.taskmanager.service;

import com.task.taskmanager.model.*;
import com.task.taskmanager.repository.UserRepository;
import com.task.taskmanager.security.JwtService;
import com.task.taskmanager.exception.ResourceNotFoundException;
import com.task.taskmanager.exception.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private static final Logger logger =
        LoggerFactory.getLogger(AuthService.class);

    public String register(RegisterRequest request) {

        
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new BadRequestException("Email already registered");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole() != null ? request.getRole() : Role.USER)
                //.role(Role.USER)
                .build();
            //admin1234
        userRepository.save(user);

        return "User Registered Successfully";
    }

    public AuthResponse login(LoginRequest request) {

        // logger.info("Login attempt for email: {}", request.getEmail());
        // User user = userRepository.findByEmail(request.getEmail())
        //         .orElseThrow(() ->
        //                 new ResourceNotFoundException("User not found with email: " + request.getEmail())
        //         );

        // if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
        //     throw new BadRequestException("Invalid credentials");
        // }

        // return jwtService.generateToken(user.getEmail(), user.getRole().name());

        logger.info("Login attempt for email: {}", request.getEmail());
        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {

        throw new BadRequestException("Invalid credentials");
    }

    String token = jwtService.generateToken(
            user.getEmail(),
            user.getRole().name()
    );

    return new AuthResponse(token, user.getRole().name());
    }
}