package com.eventdao.api.security;

import ch.qos.logback.core.util.Duration;
import com.eventdao.api.dto.customer.UserDetailDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Component
public class JwtUtils {

    //TODO refactoring add token type id
    private String jwtAccessSecret = "ZWGxLYCVMVUJWwY2Ai7eJZhnNncLeSTPenLFuAtPZo76gLRF7ZJ69tTRtGnFVPCvavYt4yMQwSJVGoGTW7Q2xewiXKgAxFvmY7e5";
    private String jwtRefreshSecret = "Egieq7DJb6tv2gKFV8agvh3Qxvro8Zna6gFyuwMwdNRu8jhDpSeiu5GmtraVVCtXwuC37UP5bfRnRKTDySEFgc7bxokNx4wfucMF";
    private String jwtPasswordResetSecret = "EU8NfLVbQYy4atujPRguQD9FTsahXroogArcKVhMDQPnyYLxFh5whxDy7yVNDrufPaDCedaMHsuf7PYg4TEL4H6TCcvMTGzJwyZg";
    private long jwtExpirationMs = Duration.buildByDays(3).getMilliseconds(); //Valid for 3 days
    private long jwtRefreshExpirationMs = Duration.buildByDays(5).getMilliseconds(); //Valid for 5 days
    private long jwtPasswordResetExpirationMs = Duration.buildByMinutes(10).getMilliseconds(); //Valid for 10m


    public String extractUsernameFromAccessSecret(String token) {
        return extractClaim(token, Claims::getSubject, jwtAccessSecret);
    }

    public String extractUsernameFromPasswordResetSecret(String token) {
        return extractClaim(token, Claims::getSubject, jwtPasswordResetSecret);
    }

    public String extractUsernameFromRefreshToken(String token) {
        return extractClaim(token, Claims::getSubject, jwtRefreshSecret);
    }

    private boolean isPasswordResetTokenExpired(String token) {
        return extractExpiration(token, jwtPasswordResetSecret).before(new Date());
    }

    private boolean isRefreshTokenExpired(String token) {
        return extractExpiration(token, jwtRefreshSecret).before(new Date());
    }

    private boolean isAccessTokenExpired(String token) {
        return extractExpiration(token, jwtAccessSecret).before(new Date());
    }

    private Date extractExpiration(String token, String secret) {
        return extractClaim(token, Claims::getExpiration, secret);
    }

    public String generateToken(UserDetailDto customer) {
        return generateToken(new HashMap<>(), customer);
    }

    public String generatePasswordResetToken(
            UserDetailDto customer
    ) {
        return buildToken(new HashMap<>(), jwtPasswordResetSecret, customer, jwtPasswordResetExpirationMs);
    }

    public String generateRefreshToken(
            UserDetailDto customer
    ) {
        return buildToken(new HashMap<>(), jwtRefreshSecret, customer, jwtRefreshExpirationMs);
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetailDto customer
    ) {
        return buildToken(extraClaims, jwtAccessSecret, customer, jwtExpirationMs);
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            String secretKey,
            UserDetails userDetails,
            long expiration
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(secretKey), SignatureAlgorithm.HS512)
                .compact();
    }

    private boolean validateToken(@NonNull String token, @NonNull Key secret) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            log.error("Token expired", expEx);
        } catch (UnsupportedJwtException unsEx) {
            log.error("Unsupported jwt", unsEx);
        } catch (MalformedJwtException mjEx) {
            log.error("Malformed jwt", mjEx);
        } catch (Exception e) {
            log.error("invalid token", e);
        }
        return false;
    }

    public boolean isRefreshTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsernameFromRefreshToken(token);
        return (username.equals(userDetails.getUsername())) && !isRefreshTokenExpired(token);
    }

    public boolean isPasswordResetTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsernameFromPasswordResetSecret(token);
        return (username.equals(userDetails.getUsername())) && !isPasswordResetTokenExpired(token);
    }

    public boolean isAccessTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsernameFromAccessSecret(token);
        return (username.equals(userDetails.getUsername())) && !isAccessTokenExpired(token);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver, String secret) {
        final Claims claims = extractAllClaims(token, secret);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token, String secret) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey(secret))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey(String secret) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

