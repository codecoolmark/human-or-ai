package at.codecool.humanoraiserver.services;

import java.util.Collection;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import at.codecool.humanoraiserver.Result;
import at.codecool.humanoraiserver.model.User;
import at.codecool.humanoraiserver.model.UserDTO;
import at.codecool.humanoraiserver.repositories.UsersRepository;

@Service
public class UsersService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public UsersService(
            UsersRepository usersRepository,
            PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Collection<User> getUsers() {
        return usersRepository.findAll();
    }

    public Result<User> registerUser(UserDTO data) {
        final User user = new User();
        user.setEmail(data.getEmail());
        user.setNickname(data.getNickname());

        final String hash = passwordEncoder.encode(data.getPassword());
        user.setPasswordHash(hash);

        try {
            return Result.of(this.usersRepository.save(user));
        } catch (DataIntegrityViolationException err) {
            return Result.error("Failed to save new user; user email may exist already");
        }
    }

    public Result<User> loginUser(UserDTO data) {
        final Optional<User> userOpt = usersRepository.findByEmail(data.getEmail());
        if (userOpt.isEmpty()) {
            return Result.error("Invalid login");
        }

        final User user = userOpt.get();

        final boolean isValidPassword = passwordEncoder.matches(
                data.getPassword(),
                user.getPasswordHash());
        if (!isValidPassword) {
            return Result.error("Invalid login");
        }

        return Result.of(user);
    }
}
