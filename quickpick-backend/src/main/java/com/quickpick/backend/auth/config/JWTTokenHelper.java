package com.quickpick.backend.auth.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

public class JWTTokenHelper {
    @Value("${jwt.auth.secret.key}")
    private String secretKey;

    @Value("${jwt.auth.app}")
    private String appName;

    @Value("${jwt.auth.expires_in}")
    private int expiresIn;


    public SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiresIn * 1000L);
    }

    public String generateJWToken(String userName) {
        return Jwts.builder()
                .issuer(appName)
                .subject(userName)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(generateExpirationDate())
                .signWith(getSigningKey())
                .compact();
    }

    public String getAuthHeaderFromHeader(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    public String getToken(HttpServletRequest request) {
        String authHeader = getAuthHeaderFromHeader(request);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return authHeader;
    }

    public Claims extractAllClaims(String authToken) {
        Claims claims;

        try{
            claims = Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(authToken)
                    .getPayload();
        }

        catch (Exception e){
            claims = null;
        }

        return claims;
    }


    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }


    // "getSubject" will get the userName because when we created the JWT, we set the subject to a userName
    public String getUserNameFromToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Boolean isTokenExpired(String token) {
        return getExpirationDateFromToken(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        String userName = getUserNameFromToken(token);

        return (userName != null && userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
