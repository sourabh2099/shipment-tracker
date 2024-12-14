package com.shipment.track.shipment_tracker.service;

import com.shipment.track.shipment_tracker.config.data.JwtTokenConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Component
public class AuthService {
    public static final String ISS = "Custom Jwt Builder";
    @Autowired
    private JwtTokenConfig jwtTokenConfig;

    public String extractUser(String token) {
        return extractClaim(token, claims -> claims.get("sub") == null ? null : claims.get("sub").toString());
    }


    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String buildToken(Map<String, Object> extraClaims,
                             UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setIssuedAt(new Date(Instant.now().toEpochMilli()))
                .setSubject(userDetails.getUsername())
                .setIssuer(ISS)
                .setExpiration(new Date(Instant.now().toEpochMilli() + jwtTokenConfig.getJwtExpirationTime()))
                .signWith(SignatureAlgorithm.HS256, getSignInKey())
                .compact();
    }

    public boolean isTokenValid(String token) {
        return isExpired(token);
    }

    private boolean isExpired(String token) {
        Date date = extractClaim(token, Claims::getIssuedAt);
        return date.before(new Date());
    }


    private Claims extractAllClaims(String token) {
        return  Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtTokenConfig.getJwtSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
