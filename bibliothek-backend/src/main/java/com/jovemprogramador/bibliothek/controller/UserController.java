package com.jovemprogramador.bibliothek.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.jovemprogramador.bibliothek.model.*;
import com.jovemprogramador.bibliothek.repository.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<User> findAll(){
        return userRepository.findAll();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{matricula}")
    public Optional<User> findByIdUser(@PathVariable String matricula) {
        return userRepository.findByMatricula(matricula);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createUser(@RequestBody User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{matricula}")
    public void updateUser(@Valid @RequestBody User user, @PathVariable String matricula) {
        if (!userRepository.existsById(matricula)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado.");
        }
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{matricula}")
    public void deleteUser(@Valid @RequestBody User user, @PathVariable String matricula) {
        userRepository.deleteById(matricula);
    }
}