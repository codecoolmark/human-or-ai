package at.codecool.humanoraiserver;

import at.codecool.humanoraiserver.config.Tokens;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final Tokens tokens;

    public JwtAuthenticationProvider(Tokens tokens) {
        this.tokens = tokens;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication instanceof JwtTokenAuthentication jwtAuthentication) {
            var userId = tokens.validateToken(jwtAuthentication.getCredentials());

            if (userId.isPresent()) {
                authentication.setAuthenticated(true);
                return authentication;
            } else {
                throw new InsufficientAuthenticationException("Invalid token");
            }
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtTokenAuthentication.class.isAssignableFrom(authentication);
    }
}
