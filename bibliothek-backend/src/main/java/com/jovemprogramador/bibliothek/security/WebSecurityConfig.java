package com.jovemprogramador.bibliothek.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    @Autowired
    private SecurityFilter securityFilter;

    @Autowired
    private CorsFilter corsFilter;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers(HttpMethod.POST,   "/api/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST,   "/api/auth/login/esqueciminhasenha").permitAll()
                        .requestMatchers(HttpMethod.PUT,    "/api/upload/perfil/**").hasAnyRole("USER","ADMIN")
                        .requestMatchers(HttpMethod.GET,    "/api/upload/perfil/**").hasAnyRole("USER","ADMIN")
                        .requestMatchers(HttpMethod.POST,   "/api/auth/register").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,    "/api/auth/renovar/credentials/list").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,    "/api/auth/user/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/auth/user/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,   "/api/auth/admin/renovarsenha").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,   "/api/livros").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,    "/api/livros/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/livros/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,   "/api/emprestimos/emprestimo/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,    "/api/emprestimos/emprestimo/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,   "/api/emprestimos/finalizar/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,   "/api/emprestimos/listar-emprestimos").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}