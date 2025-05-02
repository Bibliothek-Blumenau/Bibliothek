package com.jovemprogramador.bibliothek.controller;

import com.jovemprogramador.bibliothek.domain.auth.AuthService;
import com.jovemprogramador.bibliothek.domain.auth.dto.register.RegisterResponseDTO;
import com.jovemprogramador.bibliothek.domain.user.UserRepository;
import com.jovemprogramador.bibliothek.domain.auth.dto.login.LoginRequestDTO;
import com.jovemprogramador.bibliothek.domain.auth.dto.login.LoginResponseDTO;
import com.jovemprogramador.bibliothek.domain.auth.dto.register.RegisterRequestDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final UserRepository repository;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest){
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@RequestBody  @Valid RegisterRequestDTO registerRequest){
        var createdUser = authService.register(registerRequest);
        var location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{registration}")
                .buildAndExpand(createdUser.registration())
                .toUri();

        return ResponseEntity.created(location).body(createdUser);
    }
}

