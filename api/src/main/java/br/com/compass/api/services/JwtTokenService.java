package br.com.compass.api.services;

import br.com.compass.api.jwt.UserDetailsImpl;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import com.auth0.jwt.JWT;

@Service
public class JwtTokenService {

    private static final String SECRET_KEY = "4Z^XrroxR@dWxqf$mTTKwW$!@#qGr4P";

    private static final String ISSUER = "pizzurg-api";

//    public String generateToken(UserDetailsImpl user) {
//        try {
//            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
//            return JWT.create()
//                    .withIssuer(ISSUER)
//                    .withIssuedAt(creationDate())
//                    .withExpiresAt(expirationDate())
//                    .withSubject(user.getUsername())
//                    .sign(algorithm);
//        } catch (JWTCreationException exception){
//            throw new JWTCreationException("Error generating token.", exception);
//        }
//    }

    //witha uth
    public String generateToken(UserDetailsImpl user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 4 * 60 * 60 * 1000)) // 4 horas
                    .withSubject(user.getUsername())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new JWTCreationException("Error generating token.", exception);
        }
    }


    private Instant creationDate() {
        return ZonedDateTime.now(ZoneId.of("America/Recife")).toInstant();
    }

    private Instant expirationDate() {

        return ZonedDateTime.now(ZoneId.of("America/Recife")).plusHours(4).toInstant();
    }

    //Usando auth0 para o teste
    public String getSubjectFromToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build(); // reusable verifier
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getSubject();
        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException("Invalid or expired token.", exception);
        }
    }

    public boolean isValidToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build();
            verifier.verify(token); // Se o token é válido, isso não lança exceção
            return true;
        } catch (JWTVerificationException exception) {
            return false; // Token inválido ou expirado
        }
    }

//    public String getSubjectFromToken(String token) {
//        try {
//            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
//            return JWT.require(algorithm)
//                    .withIssuer(ISSUER)
//                    .build()
//                    .verify(token)
//                    .getSubject();
//        } catch (JWTVerificationException exception){
//            throw new JWTVerificationException("Invalid or expired.");
//        }
//    }

//    public boolean isValidToken(String token) {
//        try {
//            Claims claims = Jwts.parser()
//                    .setSigningKey(SECRET_KEY)
//                    .parseClaimsJws(token)
//                    .getBody();
//
//            return !claims.getExpiration().before(new Date());
//        } catch (Exception e) {
//            return false;
//        }
//    }
}