package com.jovemprogramador.bibliothek.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jovemprogramador.bibliothek.model.RenovarSenhaRequest;
import com.jovemprogramador.bibliothek.model.User;
import com.jovemprogramador.bibliothek.repository.UserRepository;
import com.jovemprogramador.bibliothek.security.AdminChangePasswordRequest;
import com.jovemprogramador.bibliothek.security.LoginForm;
import com.jovemprogramador.bibliothek.security.LoginResponse;
import com.jovemprogramador.bibliothek.security.RegisterForm;
import com.jovemprogramador.bibliothek.service.TokenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {
	
	@Autowired
	private PasswordEncoder passwordEncoder;

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
    
    @PostMapping("/login/esqueciminhasenha")
    public ResponseEntity<?> renovarSenha(@RequestBody RenovarSenhaRequest request){
        User existingUser = repository.findByMatriculaAndNomeCompletoIgnoreCase(request.matricula(), request.nomeCompleto());

        if(existingUser == null) {
            return ResponseEntity.badRequest().body("As informações não coincidem com a base de dados.");
        }
        
        String[] authorities = existingUser.getRoles().split(",");
        for (String authority : authorities) {
            if (authority.trim().equals("ROLE_ADMIN")) {
                return ResponseEntity.badRequest().body("Para Administradores, Entrar em contato com suporte Bibliothek.");
            }
        }

        existingUser.setResetPassword(true);
        repository.save(existingUser);
        
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/renovar/credentials/list")
    public ResponseEntity<List<User>> renovarSenhaOk(){
        List<User> existingUsers = repository.findByResetPassword(true);
        return ResponseEntity.ok(existingUsers);
    }
    
    @PostMapping("/admin/renovarsenha")
    public ResponseEntity<?> changeUserPassword(@RequestBody AdminChangePasswordRequest request){
        User existingUser = repository.findByMatricula(request.matricula());
        
        if(existingUser == null) {
            return ResponseEntity.badRequest().body("Usuário não encontrado.");
        }
        existingUser.setPassword(passwordEncoder.encode(request.password()));
        existingUser.setResetPassword(false);
        repository.save(existingUser);
        
        return ResponseEntity.ok().build();
    }
}