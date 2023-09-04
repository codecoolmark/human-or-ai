package at.codecool.humanoraiserver;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.security.auth.Subject;
import java.util.Collection;

/**
 * Authentication that is based on a JWT.
 */
public class JwtTokenAuthentication implements Authentication {

    private final String token;

    private final UserDetails userDetails;

    private boolean isAuthenticated;

    /**
     * Creates a new authentication with only a token. This implies that the token is not considered valid (yet) and no
     * user details can be provided.
     * @param token The token on which the authentication is based.
     */
    public JwtTokenAuthentication(String token) {
        this.token = token;
        this.userDetails = null;
    }

    /**
     * Creates a authentication with a token and user details. This implies that the token is valid.
     * @param token The token the authentication is based on.
     * @param userDetails The user details of the authenticated user (may be incomplete.)
     */
    public JwtTokenAuthentication(String token, UserDetails userDetails) {
        this.token = token;
        this.userDetails = userDetails;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userDetails.getAuthorities();
    }

    @Override
    public String getCredentials() {
        return this.token;
    }

    @Override
    public UserDetails getDetails() {
        return this.userDetails;
    }

    @Override
    public Object getPrincipal() {
        return this.userDetails;
    }

    @Override
    public boolean isAuthenticated() {
        return this.isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.isAuthenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return userDetails.getUsername();
    }

    @Override
    public boolean implies(Subject subject) {
        return Authentication.super.implies(subject);
    }
}
