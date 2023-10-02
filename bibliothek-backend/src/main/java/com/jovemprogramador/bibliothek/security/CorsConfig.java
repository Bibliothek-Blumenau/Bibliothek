package com.jovemprogramador.bibliothek.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Allow your frontend origin(s) here
        config.addAllowedOrigin("http://localhost:4200");
        config.addAllowedHeader("*"); // You can configure specific headers
        config.addAllowedMethod("*"); // You can configure specific HTTP methods

        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}