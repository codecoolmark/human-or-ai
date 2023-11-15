package at.codecool.humanoraiserver;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.InvalidParameterException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class Tokens {

    private final Log log = LogFactory.getLog(Tokens.class);

    private final SecretKey secret;

    private final Long timeout;

    public Tokens(@Value("${tokens.secret}") String secret, @Value("${tokens.timeout}") Long timeout) {
        if (secret.getBytes().length < 32) {
            throw new InvalidParameterException("Secret needs to be at least 32 bytes long.");
        }

        this.secret = Keys.hmacShaKeyFor(secret.getBytes());
        this.timeout = timeout;
    }

    public String generateToken(Authentication authentication) {
        var name = authentication.getName();
        var authorities = authentication.getAuthorities();
        var scopes = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .map(authorityName -> authorityName.replace("SCOPE_", ""))
                .toList();

        return Jwts.builder()
                .setSubject(name)
                .claim("scope", scopes)
                .setExpiration(Date.from(ChronoUnit.SECONDS.addTo(Instant.now(), this.timeout)))
                .signWith(this.secret)
                .compact();
    }
}
