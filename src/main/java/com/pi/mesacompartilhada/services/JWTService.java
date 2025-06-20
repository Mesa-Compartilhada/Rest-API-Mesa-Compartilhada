package com.pi.mesacompartilhada.services;

import com.pi.mesacompartilhada.models.UserPrincipal;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {

    @Value("${jwt.private.key}")
    private String privateKeyStr;
    @Value("${jwt.public.key}")
    private String publicKeyStr;

    private RSAPrivateKey privateKey;
    private RSAPublicKey publicKey;

    @PostConstruct
    public void initKeys() throws Exception {
        String privatePem = privateKeyStr.replace("\\n", "\n");
        String publicPem = publicKeyStr.replace("\\n", "\n");

        String privateBase64 = privatePem
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");
        byte[] privateBytes = Base64.getDecoder().decode(privateBase64);
        PKCS8EncodedKeySpec privateSpec = new PKCS8EncodedKeySpec(privateBytes);
        privateKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(privateSpec);

        String publicBase64 = publicPem
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");
        byte[] publicBytes = Base64.getDecoder().decode(publicBase64);
        X509EncodedKeySpec publicSpec = new X509EncodedKeySpec(publicBytes);
        publicKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(publicSpec);
    }

    public String generateToken(String id) {
        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(id)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 12))
                .and()
                .signWith(privateKey)
                .compact();
    }

    public String extractId(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(publicKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token, UserPrincipal userDetails) {
        final String id = extractId(token);
        return (id.equals(userDetails.getId()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

}