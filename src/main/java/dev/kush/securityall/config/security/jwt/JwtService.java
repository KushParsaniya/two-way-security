package dev.kush.securityall.config.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;

import javax.crypto.*;
import java.nio.charset.*;
import java.util.*;

@Service
public class JwtService {

    private String SECRET_KEY = "fe4700fdf60dae0ee0836800434070d3a24ba71aa3bc460b874151539fb2d50f";

    public SecretKey getKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .issuer("Kush Parsaniya")
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 24 * 60 * 3600))
                .signWith(getKey())
                .compact();
    }

    private Claims extractAllClaims(String token) throws Exception{
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractUserName(String token) throws Exception {
        return extractAllClaims(token).getSubject();
    }

    private Date extractExpirationDate(String token) throws Exception {
        return extractAllClaims(token).getExpiration();
    }

    private boolean isTokenExpired(String token) throws Exception {
        return extractExpirationDate(token).before(new Date());
    }

    public boolean isTokenValid(String token,UserDetails userDetails) throws Exception {
        String username = extractUserName(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }


}
