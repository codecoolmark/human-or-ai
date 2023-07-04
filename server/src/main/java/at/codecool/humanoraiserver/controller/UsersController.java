package at.codecool.humanoraiserver.controller;

import java.util.Collection;
import java.util.Optional;

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

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
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
    public Result<User> postUsersLogin(@RequestBody UserDTO userData, HttpServletResponse response) {
        final Result<User> user = usersService.loginUser(userData);

        // if (user.isEmpty()) {
        // response.setStatus(403);
        // }

        return user;
    }
}
