package at.codecool.humanoraiserver.controller;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;

import at.codecool.humanoraiserver.Result;
import at.codecool.humanoraiserver.config.Tokens;
import at.codecool.humanoraiserver.model.User;
import at.codecool.humanoraiserver.model.UserDTO;
import at.codecool.humanoraiserver.services.UsersService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class UsersController {
    private final UsersService usersService;

    private final Tokens tokens;

    private final AuthenticationManager authenticationManager;

    private final String authCookieName;

    public UsersController(UsersService usersService, AuthenticationManager authenticationManager, Tokens tokens,
                           @Value("${cookies.authcookie.name}") String authCookieName) {
        this.usersService = usersService;
        this.authenticationManager = authenticationManager;
        this.tokens = tokens;
        this.authCookieName = authCookieName;
    }

    @GetMapping("/users")
    public Collection<User> getUsers() {
        return usersService.getUsers();
    }

    @PostMapping("/users/register")
    public Result<User> postUsers(@RequestBody UserDTO userData, HttpServletResponse response) {
        final Result<User> user = usersService.registerUser(userData);

        // if (user.isError()) {
        // response.setStatus(403);
        // }

        return user;
    }

    @PostMapping("/users/login")
    public User postUsersLogin(Authentication authentication) {
        return usersService.findUserByEmail(authentication.getName());
    }

    @GetMapping("/users/current")
    public Optional<User> usersCurrent(HttpServletRequest request) {
        Cookie authCookie = WebUtils.getCookie(request, this.authCookieName);
        if (authCookie == null) {
            return Optional.empty();
        }

        Optional<String> userIdOpt = this.tokens.validateToken(authCookie.getValue());
        if (userIdOpt.isEmpty()) {
            return Optional.empty();
        }

        User user = this.usersService.findUserByEmail(userIdOpt.get());
        return Optional.of(user);
    }

    @DeleteMapping("/users/logout")
    public void usersLogout(HttpServletResponse response) {
        var authCookie = new Cookie(authCookieName, "");
        authCookie.setPath("/");
        authCookie.setMaxAge(0);
        response.addCookie(authCookie);
    }
}
