package at.codecool.humanoraiserver.services;

import java.util.Collection;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

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

    public Optional<User> registerUser(UserDTO data) {
        final User user = new User();
        user.setEmail(data.getEmail());
        user.setNickname(data.getNickname());

        final String hash = passwordEncoder.encode(data.getPassword());
        user.setPasswordHash(hash);

        try {
            return Optional.of(this.usersRepository.save(user));
        } catch (DataIntegrityViolationException err) {
            return Optional.empty();
        }
    }

    public Optional<User> loginUser(UserDTO data) {
        final Optional<User> userOpt = usersRepository.findByEmail(data.getEmail());
        if (userOpt.isEmpty()) {
            return userOpt;
        }

        final User user = userOpt.get();

        final boolean isValidPassword = passwordEncoder.isMatch(
                data.getPassword(),
                user.getPasswordHash());
        if (isValidPassword) {
            return Optional.of(user);
        }

        return Optional.empty();
    }
}
