package com.jovemprogramador.bibliothek.domain.auth;

import com.jovemprogramador.bibliothek.domain.auth.dto.register.RegisterRequestDTO;
import com.jovemprogramador.bibliothek.domain.auth.dto.register.RegisterResponseDTO;
import com.jovemprogramador.bibliothek.domain.user.UserEntity;
import com.jovemprogramador.bibliothek.domain.user.UserService;
import com.jovemprogramador.bibliothek.infrastructure.security.TokenService;
import com.jovemprogramador.bibliothek.domain.auth.dto.login.LoginRequestDTO;
import com.jovemprogramador.bibliothek.domain.auth.dto.login.LoginResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    public LoginResponseDTO login(LoginRequestDTO loginRequest) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(loginRequest.registration(), loginRequest.password());
        var auth = authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((UserEntity) auth.getPrincipal());

        return new LoginResponseDTO(token);
    }

    public RegisterResponseDTO register(RegisterRequestDTO registerRequest) {
        var encryptedPassword = new BCryptPasswordEncoder().encode(registerRequest.password());
        var newUser = UserEntity.builder()
                .registration(registerRequest.registration())
                .name(registerRequest.name())
                .password(encryptedPassword)
                .roles(registerRequest.roles())
                .build();

        return userService.create(newUser);
    }
}
