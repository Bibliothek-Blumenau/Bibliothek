package dev.williamnogueira.bibliothek.controller;

import dev.williamnogueira.bibliothek.domain.user.dto.UserRequestDTO;
import dev.williamnogueira.bibliothek.domain.user.dto.UserResponseDTO;
import dev.williamnogueira.bibliothek.domain.user.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/{registration}")
    public ResponseEntity<UserResponseDTO> findUser(@PathVariable String registration) {
        return ResponseEntity.ok(userService.findByRegistration(registration));
    }

    @PatchMapping("/{registration}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable String registration, @RequestBody UserRequestDTO updatedUser) {
        return ResponseEntity.ok(userService.updateById(registration, updatedUser));
    }

    @DeleteMapping("/{registration}")
    public ResponseEntity<Void> deleteUser(@PathVariable String registration) {
        userService.deleteById(registration);
        return ResponseEntity.noContent().build();
    }
}