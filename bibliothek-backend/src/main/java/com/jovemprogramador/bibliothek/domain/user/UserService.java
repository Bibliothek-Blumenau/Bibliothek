package com.jovemprogramador.bibliothek.domain.user;

import com.jovemprogramador.bibliothek.domain.auth.dto.register.RegisterResponseDTO;
import com.jovemprogramador.bibliothek.domain.auth.exceptions.UserAlreadyExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public RegisterResponseDTO create(UserEntity newUser) {
        if (userRepository.existsByRegistration(newUser.getRegistration())) {
            throw new UserAlreadyExistsException("User already exists with registration " + newUser.getRegistration());
        }

        userRepository.save(newUser);
        return new RegisterResponseDTO(newUser.getRegistration(), newUser.getName());
    }

    public UserEntity getEntity(String registration) {
        return userRepository.findByRegistration(registration)
                .orElseThrow(() -> new EntityNotFoundException("Usuário nao encontrado"));
    }

    public UserResponseDTO findByRegistration(String registration) {
        var user = getEntity(registration);
        return new UserResponseDTO(user.getRegistration(), user.getName(), user.getProfilePic());
    }
}
