package dev.williamnogueira.bibliothek.infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import dev.williamnogueira.bibliothek.domain.user.UserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String SECRET;

    public String generateToken(UserEntity user){
        var algorithm = Algorithm.HMAC256(SECRET);

        List<String> authorities = user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return JWT.create()
                .withIssuer("auth-api")
                .withSubject(user.getRegistration())
                .withClaim("role", authorities)
                .withExpiresAt(genExpirationDate())
                .sign(algorithm);
    }

    public String validateToken(String token){
        var algorithm = Algorithm.HMAC256(SECRET);
        return JWT.require(algorithm)
                .withIssuer("auth-api")
                .build()
                .verify(token)
                .getSubject();
    }

    private Instant genExpirationDate(){
        return LocalDateTime.now().plusHours(6).toInstant(ZoneOffset.of("-03:00"));
    }
}
