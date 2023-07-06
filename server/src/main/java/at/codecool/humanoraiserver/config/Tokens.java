package at.codecool.humanoraiserver.config;

import at.codecool.humanoraiserver.model.User;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.InvalidParameterException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@Component
public class Tokens {

    private final SecretKey secret;

    private final Long timeout;

    public Tokens(@Value("${tokens.secret}") String secret, @Value("${tokens.timeout}") Long timeout) {
        if (secret.getBytes().length < 64) {
            throw new InvalidParameterException("Secret needs to be at least 64 bytes long.");
        }

        this.secret = Keys.hmacShaKeyFor(secret.getBytes());
        this.timeout = timeout;
    }

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .claim("userName", userDetails.getUsername())
                .setExpiration(Date.from(ChronoUnit.SECONDS.addTo(Instant.now(), this.timeout)))
                .signWith(this.secret)
                .compact();
    }

    public Optional<String> validateToken(String token) {
        try {
            var claims = Jwts.parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            var username = (String) claims.get("userName");
            return Optional.of(username);
        } catch (JwtException jwtE) {
            jwtE.printStackTrace();
            return Optional.empty();
        }
    }
}
