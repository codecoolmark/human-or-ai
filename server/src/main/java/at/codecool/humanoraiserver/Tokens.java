package at.codecool.humanoraiserver;

import at.codecool.humanoraiserver.services.UserDetailsImpl;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
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
        if (secret.getBytes().length < 64) {
            throw new InvalidParameterException("Secret needs to be at least 64 bytes long.");
        }

        this.secret = Keys.hmacShaKeyFor(secret.getBytes());
        this.timeout = timeout;
    }

    public String generateToken(UserDetails userDetails) {
        var isAdmin = userDetails.getAuthorities().stream().anyMatch(grantedAuthority ->
                grantedAuthority.getAuthority().equals("isAdmin"));
        return Jwts.builder()
                .claim("userName", userDetails.getUsername())
                .claim("isAdmin", isAdmin)
                .setExpiration(Date.from(ChronoUnit.SECONDS.addTo(Instant.now(), this.timeout)))
                .signWith(this.secret)
                .compact();
    }

    public UserDetails validateToken(String token) {
        try {
            var claims = Jwts.parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            var username = (String) claims.get("userName");
            var isAdmin = claims.containsKey("isAdmin") && (Boolean) claims.get("isAdmin");
            return new UserDetailsImpl(username, token, isAdmin);
        } catch (JwtException jwtE) {
            log.debug("Validation of JWT token " + token + " failed.", jwtE);
            return null;
        }
    }
}
