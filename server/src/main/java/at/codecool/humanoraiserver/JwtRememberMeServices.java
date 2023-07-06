package at.codecool.humanoraiserver;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

@Component
public class JwtRememberMeServices implements RememberMeServices {
    private final String authCookieName;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    private final Tokens tokens;

    public JwtRememberMeServices(@Value("${cookies.authcookie.name}") String authCookieName,
                                 JwtAuthenticationProvider jwtAuthenticationProvider,
                                 Tokens tokens) {
        this.authCookieName = authCookieName;
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
        this.tokens = tokens;
    }

    @Override
    public Authentication autoLogin(HttpServletRequest request, HttpServletResponse response) {
        var cookie = WebUtils.getCookie(request, authCookieName);

        if (cookie != null) {
            var cookieValue = cookie.getValue();
            var authenticationToken = new JwtTokenAuthentication(cookieValue);

            try {
                var authentication = jwtAuthenticationProvider.authenticate(authenticationToken);

                if (!authentication.isAuthenticated()) {
                    invalidateCookie(response);
                    return null;
                }

                setAuthCookie(response, authentication);

                return authentication;

            } catch (InsufficientAuthenticationException ae) {
                invalidateCookie(response);
                return null;
            }
        }

        return null;
    }

    private void invalidateCookie(HttpServletResponse response) {
        var cookie = createAuthCookie("");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    @Override
    public void loginFail(HttpServletRequest request, HttpServletResponse response) {
        invalidateCookie(response);
    }

    @Override
    public void loginSuccess(HttpServletRequest request, HttpServletResponse response, Authentication successfulAuthentication) {
        setAuthCookie(response, successfulAuthentication);
    }

    private void setAuthCookie(HttpServletResponse response, Authentication authentication) {
        var user = (UserDetails) authentication.getPrincipal();
        response.addCookie(createAuthCookie(tokens.generateToken(user)));
    }

    private Cookie createAuthCookie(String token) {
        var authCookie = new Cookie(authCookieName, token);
        authCookie.setPath("/");
        return authCookie;
    }
}
