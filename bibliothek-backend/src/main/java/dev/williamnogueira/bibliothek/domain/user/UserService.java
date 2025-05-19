package dev.williamnogueira.bibliothek.domain.user;

import dev.williamnogueira.bibliothek.domain.auth.dto.register.RegisterResponseDTO;
import dev.williamnogueira.bibliothek.domain.auth.exceptions.UserAlreadyExistsException;
import dev.williamnogueira.bibliothek.domain.user.dto.UserRequestDTO;
import dev.williamnogueira.bibliothek.domain.user.dto.UserResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Objects.nonNull;

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

    @Transactional(readOnly = true)
    public UserResponseDTO findByRegistration(String registration) {
        var user = getEntity(registration);
        return new UserResponseDTO(user.getRegistration(), user.getName(), user.getProfilePic());
    }

    @Transactional
    public UserResponseDTO updateById(String registration, UserRequestDTO updatedUser) {
        var user = getEntity(registration);
        if (nonNull(updatedUser.registration())) {
            user.setRegistration(updatedUser.registration());
        }

        if (nonNull(updatedUser.name())) {
            user.setName(updatedUser.name());
        }

        if (nonNull(updatedUser.password())) {
            user.setPassword(new BCryptPasswordEncoder().encode(updatedUser.password()));
        }

        if (nonNull(updatedUser.profilePic())) {
            user.setProfilePic(updatedUser.profilePic());
        }

        userRepository.save(user);
        return new UserResponseDTO(user.getRegistration(), user.getName(), user.getProfilePic());
    }

    @Transactional
    public void deleteById(String registration) {
        var user = getEntity(registration);
        userRepository.delete(user);
    }

    public UserEntity getEntity(String registration) {
        return userRepository.findByRegistration(registration)
                .orElseThrow(() -> new EntityNotFoundException("Usuário nao encontrado"));
    }
}
