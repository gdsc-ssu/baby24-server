package com.gdgoc.baby24.service;

import com.gdgoc.baby24.dto.LoginRequest;
import com.gdgoc.baby24.dto.LoginResponse;
import com.gdgoc.baby24.domain.Users;
import com.gdgoc.baby24.exception.AuthenticationException;
import com.gdgoc.baby24.repository.UserRepository;
import com.gdgoc.baby24.util.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public LoginResponse login(LoginRequest loginRequest) {
        // 사용자 조회
        Users user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new AuthenticationException("User not found"));

        // 비밀번호 비교
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new AuthenticationException("Invalid credentials");
        }

        // JWT 토큰 생성
        String token = jwtTokenProvider.generateToken(user);

        return new LoginResponse(token, user.getEmail(), user.getName());
    }
}
