package com.brunopiovan.ProjetoSpring.infra.secutiry;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.brunopiovan.ProjetoSpring.domain.user.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secreta;

    public String generateToken(Usuario user){

        try {
            Algorithm algorithm = Algorithm.HMAC256(secreta);

            String token = JWT.create()
                    .withIssuer("ProjetoSpring")
                    .withSubject(user.getEmail())
                    .withExpiresAt(this.generateExperiation())
                    .sign(algorithm);
            return token;
        }catch (JWTCreationException exception){
            throw new RuntimeException("Erro enquanto estava autenticando.");
        }
    }

    public String validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secreta);
            return JWT.require(algorithm)
                    .withIssuer("ProjetoSpring")
                    .build()
                    .verify(token)
                    .getSubject();
        }catch (JWTVerificationException exception){
            return null;
        }
    }

    private Instant generateExperiation(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
