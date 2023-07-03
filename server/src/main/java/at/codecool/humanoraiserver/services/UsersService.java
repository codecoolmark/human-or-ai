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

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public Collection<User> getUsers() {
        return usersRepository.findAll();
    }

    public Optional<User> registerUser(UserDTO data) {
        final User user = new User();
        user.setEmail(data.getEmail());
        user.setNickname(data.getNickname());
        user.setPasswordHash(data.getPassword()); // TODO: password hashing

        try {
            return Optional.of(this.usersRepository.save(user));
        } catch (DataIntegrityViolationException err) {
            return Optional.empty();
        }
    }
    }
}
