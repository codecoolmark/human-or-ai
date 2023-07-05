package at.codecool.humanoraiserver.controller;

import java.util.Collection;

import at.codecool.humanoraiserver.config.Tokens;
import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.codecool.humanoraiserver.Result;
import at.codecool.humanoraiserver.model.User;
import at.codecool.humanoraiserver.model.UserDTO;
import at.codecool.humanoraiserver.services.UsersService;
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

    @PostMapping("/users")
    public Result<User> postUsers(@RequestBody UserDTO userData, HttpServletResponse response) {
        final Result<User> user = usersService.registerUser(userData);

        // if (user.isError()) {
        // response.setStatus(403);
        // }

        return user;
    }

    @PostMapping("/users/login")
    public User postUsersLogin(@RequestBody UserDTO userData, HttpServletResponse response) {
        var token = new UsernamePasswordAuthenticationToken(userData.getEmail(), userData.getPassword());
        this.authenticationManager.authenticate(token);
        var user = usersService.findUserByEmail(userData.getEmail());
        var authCookie = new Cookie(authCookieName, tokens.generateToken(user));
        authCookie.setPath("/");
        response.addCookie(authCookie);
        return user;
    }
}
