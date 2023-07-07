package at.codecool.humanoraiserver.services;

import java.util.Collection;
import java.util.Random;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import at.codecool.humanoraiserver.Result;
import at.codecool.humanoraiserver.model.User;
import at.codecool.humanoraiserver.controller.PostUsersRequest;
import at.codecool.humanoraiserver.repositories.UsersRepository;

@Service
public class UsersService implements UserDetailsService {
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

    public Result<User> registerUser(PostUsersRequest data) {
        final User user = new User();
        user.setEmail(data.getEmail());
        user.setNickname(data.getNickname());
        user.setQuoteSeed(new Random().nextLong());

        final String hash = passwordEncoder.encode(data.getPassword());
        user.setPasswordHash(hash);

        try {
            return Result.of(this.usersRepository.save(user));
        } catch (DataIntegrityViolationException err) {
            return Result.error("Failed to save new user; user email may exist already");
        }
    }

    public User findUserByEmail(String email) {
        return usersRepository.findByEmail(email).orElseThrow();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usersRepository.findByEmail(username)
                .map(UserDetailsImpl::new)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found!"));
    }

    public User findUserById(Long id) {
        return usersRepository.findById(id).orElseThrow();
    }
}
