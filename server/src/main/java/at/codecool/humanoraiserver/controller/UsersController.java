package at.codecool.humanoraiserver.controller;

import at.codecool.humanoraiserver.services.CreateUserResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import at.codecool.humanoraiserver.Tokens;
import at.codecool.humanoraiserver.services.UsersService;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

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
    public ResponseEntity<CreateUserResult> postUsers(@RequestBody PostUsersRequest userData, HttpServletResponse response) {
        var result = usersService.registerUser(userData);
        var statusCode = HttpStatus.OK;

        if (result.getUser().isEmpty()) {
            statusCode = HttpStatus.CONFLICT;
        }

        return new ResponseEntity<>(result, statusCode);
    }
}
