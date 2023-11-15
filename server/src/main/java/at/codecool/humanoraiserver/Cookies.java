package at.codecool.humanoraiserver;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class Cookies {

    private final Tokens tokens;

    private final String authCookieName;

    public Cookies(@Value("${cookies.authcookie.name}") String authCookieName, Tokens tokens) {
        this.tokens = tokens;
        this.authCookieName = authCookieName;
    }

    public void setCookie(UserDetails userDetails, HttpServletResponse response) {
        response.addCookie(createAuthCookie(tokens.generateToken(userDetails)));
    }

    public void addCookie(HttpServletResponse response, String token) {
        var authCookie = new Cookie(authCookieName, token);
        authCookie.setPath("/");
        response.addCookie(authCookie);
    }

    private Cookie createAuthCookie(String token) {
        var authCookie = new Cookie(authCookieName, token);
        authCookie.setPath("/");
        return authCookie;
    }

    public void removeCookie(HttpServletResponse response) {
        var cookie = createAuthCookie("");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
