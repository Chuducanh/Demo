package com.example.pbl7_backend.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${security.jwt.secret-key}")
    private String jwtSecretKey;

    @Getter
    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;


    /**
     * Extract username from JWT string
     *
     * @param token JWT string
     * @return The username string
     */
    public String extractUsername(final String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extract expiration time from JWT string
     *
     * @param token JWT string
     * @return Expiration time
     */
    public Date extractExpiration(final String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Validate JWT string
     *
     * @param token       JWT string
     * @param userDetails Object that implement UserDetails interface
     * @return True if username from userDetails and expiration time is valid, return true. Otherwise, it's false
     */
    public boolean isTokenValid(final String token, final UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername())
                && !isTokenExpired(token);
    }

    /**
     * Validate expiration of the JWT
     *
     * @param token JWT string
     * @return True if the expiration time of the JWT is after now. Otherwise, it's false
     */
    private boolean isTokenExpired(final String token) {
        return extractExpiration(token)
                .before(new Date(System.currentTimeMillis()));
    }

    /**
     * Extract a Claim from Claims
     *
     * @param token    JWT Token string
     * @param resolver Functional Interface
     * @return A functional interface resolver that takes claims as argument
     */
    public <T> T extractClaim(
            String token,
            Function<Claims, T> resolver
    ) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    /**
     * Extract Payload/Claims from the token
     *
     * @param token JWT Token string
     * @return All claims that extract from the JWT Token string
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return generateToken(extraClaims, userDetails, jwtExpiration);
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userdetails,
            long expiration
    ) {
        return Jwts
                .builder()
                .claims(extraClaims)
                .subject(userdetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey())
                .compact();
    }

    private SecretKey getSigningKey() {
//        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        byte[] keyBytes = jwtSecretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
