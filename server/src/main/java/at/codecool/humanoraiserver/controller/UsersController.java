package at.codecool.humanoraiserver.controller;

import java.util.Collection;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.codecool.humanoraiserver.model.User;
import at.codecool.humanoraiserver.model.UserDTO;
import at.codecool.humanoraiserver.services.UsersService;

@RestController
public class UsersController {
    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/users")
    public Collection<User> getUsers() {
        return usersService.getUsers();
    }

    @PostMapping("/users")
    public Optional<User> postUsers(@RequestBody UserDTO user) {
        return usersService.registerUser(user);
    }
}
