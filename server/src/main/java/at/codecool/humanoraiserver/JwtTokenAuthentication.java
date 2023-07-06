package at.codecool.humanoraiserver;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.security.auth.Subject;
import java.util.Collection;

public class JwtTokenAuthentication implements Authentication {

    private final String token;

    private UserDetails userDetails;

    private boolean isAuthenticated;

    public JwtTokenAuthentication(String token) {
        this.token = token;
        this.userDetails = null;
    }

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
