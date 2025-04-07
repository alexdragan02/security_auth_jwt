package com.example.pretesting_mtls_client.auth.services;

import com.example.pretesting_mtls_client.auth.dtos.AuthenticationRequest;
import com.example.pretesting_mtls_client.auth.dtos.AuthenticationResponse;
import com.example.pretesting_mtls_client.auth.dtos.RegisterRequest;
import com.example.pretesting_mtls_client.auth.jwt.JwtService;
import com.example.pretesting_mtls_client.auth.models.Role;
import com.example.pretesting_mtls_client.auth.models.User;
import com.example.pretesting_mtls_client.auth.repositories.UserRepository;
import io.jsonwebtoken.Jwt;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request){
        var user= User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        var jwtToken=jwtService.generateToken(user);
        var refreshToken=jwtService.generateRefreshToken(user);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user=userRepository.findByUsername(request.getUsername()).orElseThrow();
        var token=jwtService.generateToken(user);
        var refreshToken=jwtService.generateRefreshToken(user);

        return AuthenticationResponse.builder()
                .accessToken(token)
                .refreshToken(refreshToken)
                .build();
    }
}
