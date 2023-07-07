package at.codecool.humanoraiserver.controller;

import at.codecool.humanoraiserver.services.UsersService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SessionController {

    private final String authCookieName;

    private final UsersService usersService;

    public SessionController(@Value("${cookies.authcookie.name}") String authCookieName, UsersService usersService) {
        this.authCookieName = authCookieName;
        this.usersService = usersService;
    }

    @GetMapping("/session")
    public SessionResponse getSession(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            var user = usersService.findUserByEmail(authentication.getName());
            return new SessionResponse(user);
        } else {
            throw new InsufficientAuthenticationException("Not authenticated");
        }
    }

    @PostMapping("/session")
    public SessionResponse postSession(Authentication authentication) {
        return new SessionResponse(usersService.findUserByEmail(authentication.getName()));
    }

    @DeleteMapping("/session")
    public void deleteSession(HttpServletResponse response) {
        var cookie = new Cookie(this.authCookieName, "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
