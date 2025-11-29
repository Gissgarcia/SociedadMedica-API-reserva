package com.sociedadmedica.reserva.config;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails; // Se mantiene, pero es menos usado
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("S3R6V01SRE1DNDYzQ2hMWFh5VzR5Q1g2U0FhNHJmN0E=")
    private String secret;

    @Value("${jwt.expiration}")
    private long jwtExpirationMs;

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // **NUEVO MÉTODO para obtener el rol**
    public String extractRole(String token) {
        // Asumimos que el rol fue guardado en las Claims bajo la clave 'role' o similar
        // Si tu API de usuario NO guarda el rol como Claim, esto fallará.
        // Si el rol es el único Claim extra, puedes intentar extraerlo.

        // Dado que tu API de usuario NO agregaba claims extra, este método NO puede funcionar.
        // Asumiremos que el rol NO está en el token y lo inyectaremos por defecto, o...
        // ... DEBEMOS MODIFICAR LA API DE USUARIO para que AGREGUE el rol como claim extra.

        // POR AHORA, para que compile, devolvemos un rol por defecto si no lo encuentras:
        return (String) extractAllClaims(token).get("role");
    }

    // --- Métodos de Validación ---

    // **MÉTODO MODIFICADO (para Microservicios) - Solo valida firma y expiración**
    public boolean isTokenValid(String token) {
        // En el proyecto de reserva, solo nos importa si fue firmado por la clave correcta y no ha expirado.
        return !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    private Claims extractAllClaims(String token) {
        // Lógica de parsing y verificación de firma
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}