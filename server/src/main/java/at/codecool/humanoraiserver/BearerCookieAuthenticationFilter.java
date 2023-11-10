package at.codecool.humanoraiserver;

import at.codecool.humanoraiserver.services.UserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import java.io.IOException;

@Component
public class BearerCookieAuthenticationFilter extends OncePerRequestFilter {


    private final String authCookieName;

    private final AuthenticationManager authenticationManager;

    private final Tokens tokens;

    public BearerCookieAuthenticationFilter(@Value("${cookies.authcookie.name}") String authCookieName,
                                            AuthenticationManager authenticationManager,
                                            Tokens tokens) {
        this.authCookieName = authCookieName;
        this.authenticationManager = authenticationManager;
        this.tokens = tokens;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var cookie = WebUtils.getCookie(request, authCookieName);

        if (cookie != null) {
            var cookieValue = cookie.getValue();
            var authentication = new BearerTokenAuthenticationToken(cookieValue);
            var newContext = SecurityContextHolder.createEmptyContext();

            try {
                var newAuthentication = authenticationManager.authenticate(authentication);

                if (newAuthentication.isAuthenticated()) {
                    newContext.setAuthentication(newAuthentication);
                    SecurityContextHolder.setContext(newContext);
                }
            } catch (InvalidBearerTokenException e){
                invalidateCookie(response);
            }

        }

        filterChain.doFilter(request, response);

        if (SecurityContextHolder.getContext().getAuthentication() != null && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            // todo ms 2023-11-10 this breaks logout find a fix.
            setAuthCookie(response, SecurityContextHolder.getContext().getAuthentication());
        } else {
            invalidateCookie(response);
        }
    }

    private void invalidateCookie(HttpServletResponse response) {
        var cookie = createAuthCookie("");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    private void setAuthCookie(HttpServletResponse response, Authentication authentication) {
        if (authentication.getPrincipal() instanceof UserDetailsImpl) {
            var user = (UserDetails) authentication.getPrincipal();
            response.addCookie(createAuthCookie(tokens.generateToken(user)));
        } else {
            var user = (Jwt) authentication.getPrincipal();
            response.addCookie(createAuthCookie(tokens.generateToken(user)));
        }
    }

    private Cookie createAuthCookie(String token) {
        var authCookie = new Cookie(authCookieName, token);
        authCookie.setPath("/");
        return authCookie;
    }
}
