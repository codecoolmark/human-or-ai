package at.codecool.humanoraiserver;

import at.codecool.humanoraiserver.services.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.security.auth.Subject;
import java.util.Collection;

public class JwtTokenAuthentication implements Authentication {

    private final String token;

    private UserDetailsImpl userDetails;

    private boolean isAuthenticated;

    public JwtTokenAuthentication(String token) {
        this.token = token;
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
    public UserDetailsImpl getDetails() {
        return this.userDetails;
    }

    @Override
    public Object getPrincipal() {
        return null;
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

    public void setUserDetails(UserDetailsImpl userDetailsImpl) {
        this.userDetails = userDetailsImpl;
    }
}
