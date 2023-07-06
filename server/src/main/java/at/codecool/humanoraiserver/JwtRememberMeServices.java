package at.codecool.humanoraiserver;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
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
            return jwtAuthenticationProvider.authenticate(authenticationToken);
        }

        return null;
    }

    @Override
    public void loginFail(HttpServletRequest request, HttpServletResponse response) {
        // In case a login fails we need to do exactly nothing
    }

    @Override
    public void loginSuccess(HttpServletRequest request, HttpServletResponse response, Authentication successfulAuthentication) {
        var user = (UserDetails) successfulAuthentication.getPrincipal();
        var authCookie = new Cookie(authCookieName, tokens.generateToken(user));
        authCookie.setPath("/");
        response.addCookie(authCookie);
    }
}
