package at.codecool.humanoraiserver.services;

import java.util.Collection;
import java.util.Optional;

import org.springframework.stereotype.Service;

import at.codecool.humanoraiserver.model.User;
import at.codecool.humanoraiserver.repositories.UsersRepository;

@Service
public class UsersService {
    private final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public Collection<User> getUsers() {
        return usersRepository.getUsers();
    }

    public Optional<User> registerUser(String email, String nickname, String password) {
        // TODO
        final String passwordHash = password;
        final User user = new User(email, nickname, passwordHash);

        return Optional.of(this.usersRepository.saveUser(user));
    }
}
