package dev.williamnogueira.bibliothek.domain.auth;

import dev.williamnogueira.bibliothek.domain.auth.dto.register.RegisterRequestDTO;
import dev.williamnogueira.bibliothek.domain.auth.dto.register.RegisterResponseDTO;
import dev.williamnogueira.bibliothek.domain.user.UserEntity;
import dev.williamnogueira.bibliothek.domain.user.UserService;
import dev.williamnogueira.bibliothek.infrastructure.security.TokenService;
import dev.williamnogueira.bibliothek.domain.auth.dto.login.LoginRequestDTO;
import dev.williamnogueira.bibliothek.domain.auth.dto.login.LoginResponseDTO;
import lombok.RequiredArgsConstructor;
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
