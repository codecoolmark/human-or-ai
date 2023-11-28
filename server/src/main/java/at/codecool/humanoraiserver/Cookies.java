package at.codecool.humanoraiserver;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import java.util.Optional;

@Component
public class Cookies {

    private final String authCookieName;

    public Cookies(@Value("${cookies.authcookie.name}") String authCookieName) {
        this.authCookieName = authCookieName;
    }

    public void addCookie(HttpServletResponse response, String token) {
        var authCookie = createAuthCookie(token);
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

    public Optional<String> getToken(HttpServletRequest request) {
        return Optional.ofNullable(WebUtils.getCookie(request, authCookieName))
                .map(Cookie::getValue);
    }
}
