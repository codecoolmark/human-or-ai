package at.codecool.humanoraiserver;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class UpdateCookieFilter extends OncePerRequestFilter {

    private final Tokens tokens;

    private final Cookies cookies;

    public UpdateCookieFilter(Tokens tokens,
                              Cookies cookies) {
        this.tokens = tokens;
        this.cookies = cookies;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        filterChain.doFilter(request, response);

        if (SecurityContextHolder.getContext().getAuthentication() != null
                && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            this.cookies.addCookie(response, tokens.generateToken(SecurityContextHolder.getContext().getAuthentication()));
        } else {
            this.cookies.removeCookie(response);
        }
    }
}
