package com.dreamsoft.ticketing.security.jwt;

import com.dreamsoft.ticketing.entity.enumerations.CustomMessage;
import com.dreamsoft.ticketing.exceptions.CustomException;
import com.dreamsoft.ticketing.security.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtUtils {
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.jwtExpirationMs}")
    private Long jwtExpirationMs;

    public String generateJwtToken(UserDetailsImpl userPrincipal) {

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .claim("authorities", userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
    public String generateJwtToken(String username) {
        return Jwts.builder()
                .setSubject((username))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) throws CustomException  {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException e) {

            throw new CustomException(CustomMessage.INTERNAL_ERROR);
        } catch (ExpiredJwtException e) {
            throw new CustomException(CustomMessage.INTERNAL_ERROR);
        } catch (UnsupportedJwtException e) {

            throw new CustomException(CustomMessage.INVALID_TOKEN);
        } catch (IllegalArgumentException e) {
            throw new CustomException(CustomMessage.INVALID_TOKEN);
        }

    }


    public boolean isTokenExpired(String token) {
        Date expiredDate=extractExpiration(token);
        return expiredDate.before(new Date());
    }


    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    public <T> T extractClaim(String token , Function<Claims, T> claimResolver) {
        final Claims claim= extractAllClaims(token);
        return claimResolver.apply(claim);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    }
}
