package at.codecool.humanoraiserver.repositories;

import java.util.Collection;
import java.util.HashMap;

import org.springframework.stereotype.Repository;

import at.codecool.humanoraiserver.model.User;

@Repository
public class UsersRepository {
    // TODO
    private final HashMap<Integer, User> users;
    private int nextId;

    public UsersRepository() {
        this.users = new HashMap<>();
        this.nextId = 0;
    }

    public User saveUser(User user) {
        final int id = this.nextId++;
        this.users.put(id, user);
        return user;
    }

    public Collection<User> getUsers() {
        return this.users.values();
    }
}
