package at.codecool.humanoraiserver.controller;

import at.codecool.humanoraiserver.services.UsersService;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SessionController {

    private final UsersService usersService;

    public SessionController(UsersService usersService) {
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
    public void deleteSession(Authentication authentication) {
        authentication.setAuthenticated(false);
    }
}
