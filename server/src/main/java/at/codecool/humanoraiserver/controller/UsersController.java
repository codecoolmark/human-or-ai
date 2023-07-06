package at.codecool.humanoraiserver.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.codecool.humanoraiserver.Result;
import at.codecool.humanoraiserver.Tokens;
import at.codecool.humanoraiserver.model.User;
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

    @PostMapping("/users")
    public Result<User> postUsers(@RequestBody PostUsersRequest userData, HttpServletResponse response) {
        final Result<User> user = usersService.registerUser(userData);
        return user;
    }
}
