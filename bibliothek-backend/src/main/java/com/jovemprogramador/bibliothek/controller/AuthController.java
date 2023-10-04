package com.jovemprogramador.bibliothek.controller;

import com.jovemprogramador.bibliothek.model.User;
import com.jovemprogramador.bibliothek.repository.UserRepository;
import com.jovemprogramador.bibliothek.security.LoginForm;
import com.jovemprogramador.bibliothek.security.LoginResponse;
import com.jovemprogramador.bibliothek.security.RegisterForm;
import com.jovemprogramador.bibliothek.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository repository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    private ResponseEntity<?> login(@RequestBody LoginForm loginRequest){
        var usernamePassword = new UsernamePasswordAuthenticationToken(loginRequest.matricula(), loginRequest.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody  @Valid RegisterForm registerRequest){
        if(this.repository.findByMatricula(registerRequest.matricula()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerRequest.password());
        User newUser = new User(registerRequest.matricula(),registerRequest.nomeCompleto(), encryptedPassword, registerRequest.roles(), "");
        this.repository.save(newUser);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/user/{matricula}/foto")
    public ResponseEntity<?> updateFoto(@PathVariable String matricula, @RequestBody User updatedUser) {
        User existingUser = repository.findByMatricula(matricula);
        if (existingUser == null) {
            return ResponseEntity.notFound().build();
        }
        existingUser.setFotoPerfil(updatedUser.getFotoPerfil());
        repository.save(existingUser);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/user/{matricula}/senha")
    public ResponseEntity<?> updateSenha(@PathVariable String matricula, @RequestBody User updatedUser) {
        User existingUser = repository.findByMatricula(matricula);
        if (existingUser == null) {
            return ResponseEntity.notFound().build();
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(updatedUser.getPassword());
        existingUser.setPassword(encryptedPassword);
        repository.save(existingUser);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/user/{matricula}")
    public ResponseEntity<?> deleteUser(@PathVariable String matricula) {
        User existingUser = repository.findByMatricula(matricula);
        if (existingUser == null) {
            return ResponseEntity.notFound().build();
        }
        repository.delete(existingUser);

        return ResponseEntity.ok().build();
    }
}

