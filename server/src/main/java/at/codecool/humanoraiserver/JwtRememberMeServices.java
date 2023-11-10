package at.codecool.humanoraiserver;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

/**
 * JWT based remember me services. This class allows a request to be authenticated based on a JWT stored in a cookie.
 * The name of the cookie can be configured using the {@code cookies.authcookie.name} setting. If a request is sent with
 * this cookie the token is read from the cookie and validated using the {@link org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider}.
 */
@Component
public class JwtRememberMeServices implements RememberMeServices {
    private final String authCookieName;
    private final AuthenticationManager authenticationManager;

    private final Tokens tokens;


    /**
     * Creates a new service.
     * @param authCookieName The name of the cookie that contains the authentication token.
     * @param authenticationManager The {@link AuthenticationManager} used to validate the token.
     * @param tokens Utility class for generating the tokens.
     */
    public JwtRememberMeServices(@Value("${cookies.authcookie.name}") String authCookieName,
                                 AuthenticationManager authenticationManager,
                                 Tokens tokens) {
        this.authCookieName = authCookieName;
        this.authenticationManager = authenticationManager;
        this.tokens = tokens;
    }

    @Override
    public Authentication autoLogin(HttpServletRequest request, HttpServletResponse response) {
        var cookie = WebUtils.getCookie(request, authCookieName);

        // TODO ms 2023.11.03 regenerate token on every request
        if (cookie != null) {
            var cookieValue = cookie.getValue();
            var authenticationToken = new BearerTokenAuthenticationToken(cookieValue);
            return authenticationToken;
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
        if (authentication.getPrincipal() instanceof Jwt) {
            var user = (Jwt) authentication.getPrincipal();
            response.addCookie(createAuthCookie(tokens.generateToken(user)));
        } else {
            authentication.getPrincipal();
        }

    }

    private Cookie createAuthCookie(String token) {
        var authCookie = new Cookie(authCookieName, token);
        authCookie.setPath("/");
        return authCookie;
    }
}
