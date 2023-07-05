package at.codecool.humanoraiserver;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class JwtRemberMeService implements RememberMeServices {
    private final String authCookieName;

    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    public JwtRemberMeService(@Value("${cookies.authcookie.name}") String authCookieName, JwtAuthenticationProvider jwtAuthenticationProvider, AuthenticationManager authenticationManager) {
        this.authCookieName = authCookieName;
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
    }

    @Override
    public Authentication autoLogin(HttpServletRequest request, HttpServletResponse response) {
        if (request.getCookies() == null) {
            return null;
        }

        // TODO: this try/catch block may be unnecessary once we reset the auth cookie when login expired
        try {
            var authentication = Arrays.stream(request.getCookies())
                    .filter(cookie -> cookie.getName().equals(authCookieName))
                    .findAny()
                    .map(Cookie::getValue)
                    .map(JwtTokenAuthentication::new)
                    .map(jwtAuthenticationProvider::authenticate);

            if (authentication.isPresent()) {
                return authentication.get();
            } else {
                return null;
            }
        } catch (AuthenticationException e) {
            return null;
        }
    }

    @Override
    public void loginFail(HttpServletRequest request, HttpServletResponse response) {
        // TODO ms 2023.07.05 get this method called and reset cookie
        System.out.println("loginFail");
    }

    @Override
    public void loginSuccess(HttpServletRequest request, HttpServletResponse response, Authentication successfulAuthentication) {
        // TODO ms 2023.07.05 get this method called and set cookie
        System.out.println("loginSuccess");
    }
}
