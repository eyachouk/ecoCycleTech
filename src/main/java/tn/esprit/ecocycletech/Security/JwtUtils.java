package tn.esprit.ecocycletech.Security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import tn.esprit.ecocycletech.Entity.UserManagement.User;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtils {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration.ms:86400000}") // Default 24 hours
    private long jwtExpirationMs;

    @Value("${jwt.refresh-window.ms:1800000}") // Default 30 minutes
    private long refreshWindowMs;

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        // Add additional claims
        if (userDetails instanceof User) {
            User user = (User) userDetails;
            claims.put("id", user.getIdUser());
            claims.put("role", user.getRole().name());
            claims.put("email", user.getEmail());
        }
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Map<String, Object> validateAndExtractClaims(String token) {
        Map<String, Object> result = new HashMap<>();

        try {
            Claims claims = extractAllClaims(token);
            result.put("valid", true);
            result.put("claims", claims);
        } catch (Exception e) {
            result.put("valid", false);
            result.put("error", e.getMessage());
        }

        return result;
    }



    public boolean validateTokenFilter(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String generateTokenForOAuth2User(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getIdUser());
        claims.put("email", user.getEmail());
        claims.put("role", user.getRole().name());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs)) // 10 hours
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    public String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean canTokenBeRefreshed(String token) {
        return (!isTokenExpired(token) || isWithinRefreshWindow(token));
    }

    private boolean isWithinRefreshWindow(String token) {
        final Date expiration = extractExpiration(token);
        final Date now = new Date();
        // Allow refresh if token expired within last 30 minutes
        return expiration.before(now) &&
                (now.getTime() - expiration.getTime()) < refreshWindowMs;
    }
    public Long extractUserId(String token) {
        return extractClaim(token, claims -> claims.get("id", Long.class));
    }
    public String extractRole(String token) {
        return extractClaim(token, claims -> claims.get("role", String.class));
    }
}
