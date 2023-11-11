package com.example.animal_search_engine.service.impl;

import com.example.animal_search_engine.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {
    //@Value("${token.signing.key}")
    // Здесь можно указать значение ключа для подписи JWT (не рекомендуется хранить ключ в коде)
    private String jwtSigningKey="413F4428472B4B6250655368566D5970337336763979244226452948404D6351";
    @Override
    // Извлекаем имя пользователя из JWT-токена, используя метод extractClaim
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    // Генерируем новый JWT-токен на основе информации о пользователе
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        // Извлекаем имя пользователя из JWT-токена
        final String userName = extractUserName(token);

        // Проверяем, совпадает ли имя пользователя из токена с именем пользователя из UserDetails
        // и не истек ли срок действия токена
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    // Этот метод извлекает утверждение (claim) из JWT-токена, используя переданный метод
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);// Извлекаем все утверждения из JWT-токена


        // Применяем переданный метод (например, Claims::getSubject) к извлеченным утверждениям
        return claimsResolvers.apply(claims);
    }

    // Этот метод генерирует новый JWT-токен на основе информации о пользователе
    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())// Устанавливаем имя пользователя в токен
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))// Устанавливаем срок действия токена
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();// Подписываем токен
    }


    // Этот метод проверяет, истек ли срок действия JWT-токена
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Этот метод извлекает срок действия токена из JWT-токена, используя метод extractClaim
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


    // Этот метод извлекает все утверждения из JWT-токена и проверяет подпись
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token)
                .getBody();
    }


    // Этот метод получает ключ подписи из строки и возвращает объект Key
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

