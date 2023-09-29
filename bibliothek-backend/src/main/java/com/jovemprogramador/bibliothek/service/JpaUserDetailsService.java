package com.jovemprogramador.bibliothek.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jovemprogramador.bibliothek.model.SecurityUser;
import com.jovemprogramador.bibliothek.repository.UserRepository;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public JpaUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String matricula) throws UsernameNotFoundException {
        return userRepository.findByMatricula(matricula)
                .map(SecurityUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario não encontrado, matricula: " + matricula));

    }

}
