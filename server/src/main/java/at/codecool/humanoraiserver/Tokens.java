package at.codecool.humanoraiserver;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class Tokens {

    private final Log log = LogFactory.getLog(Tokens.class);

    private final Long timeout;

    private final JwtEncoder jwtEncoder;

    private final MacAlgorithm macAlgorithm;

    public Tokens(@Value("${tokens.timeout}") Long timeout,
                  JwtEncoder jwtEncoder,
                  @Value("${tokens.macalgorithm}") MacAlgorithm macAlgorithm) {
        this.timeout = timeout;
        this.jwtEncoder = jwtEncoder;
        this.macAlgorithm = macAlgorithm;
    }

    public String generateToken(Authentication authentication) {
        var name = authentication.getName();
        var authorities = authentication.getAuthorities();
        var scopes = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .map(authorityName -> authorityName.replace("SCOPE_", ""))
                .toList();

        return jwtEncoder.encode(JwtEncoderParameters.from(
                JwsHeader.with(this.macAlgorithm)
                        .build(),
                JwtClaimsSet.builder()
                        .subject(name)
                        .claim("scope", scopes)
                        .expiresAt(ChronoUnit.SECONDS.addTo(Instant.now(), this.timeout))
                .build())).getTokenValue();
    }
}
