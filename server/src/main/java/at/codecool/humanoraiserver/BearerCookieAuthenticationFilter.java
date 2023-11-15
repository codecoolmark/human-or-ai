package at.codecool.humanoraiserver;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class BearerCookieAuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;

    private final Tokens tokens;

    private final Cookies cookies;

    public BearerCookieAuthenticationFilter(AuthenticationManager authenticationManager,
                                            Tokens tokens,
                                            Cookies cookies) {
        this.authenticationManager = authenticationManager;
        this.tokens = tokens;
        this.cookies = cookies;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var tokenOptional = this.cookies.getToken(request);

        tokenOptional.ifPresent(token -> {
            var authentication = new BearerTokenAuthenticationToken(token);
            var newContext = SecurityContextHolder.createEmptyContext();

            try {
                var newAuthentication = authenticationManager.authenticate(authentication);

                if (newAuthentication.isAuthenticated()) {
                    newContext.setAuthentication(newAuthentication);
                    SecurityContextHolder.setContext(newContext);
                }
            } catch (InvalidBearerTokenException e){
                this.cookies.removeCookie(response);
            }
        });

        filterChain.doFilter(request, response);

        if (SecurityContextHolder.getContext().getAuthentication() != null
                && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            setAuthCookie(response, SecurityContextHolder.getContext().getAuthentication());
        } else {
            this.cookies.removeCookie(response);
        }
    }

    private void setAuthCookie(HttpServletResponse response, Authentication authentication) {
        var token = tokens.generateToken(authentication);
        this.cookies.addCookie(response, token);
    }
}
