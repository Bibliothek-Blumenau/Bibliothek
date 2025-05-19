package dev.williamnogueira.bibliothek.controller;

import dev.williamnogueira.bibliothek.domain.auth.AuthService;
import dev.williamnogueira.bibliothek.domain.auth.dto.register.RegisterResponseDTO;
import dev.williamnogueira.bibliothek.domain.auth.dto.login.LoginRequestDTO;
import dev.williamnogueira.bibliothek.domain.auth.dto.login.LoginResponseDTO;
import dev.williamnogueira.bibliothek.domain.auth.dto.register.RegisterRequestDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@RequestBody @Valid RegisterRequestDTO registerRequest) {
        var createdUser = authService.register(registerRequest);
        var location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{registration}")
                .buildAndExpand(createdUser.registration())
                .toUri();

        return ResponseEntity.created(location).body(createdUser);
    }
}

