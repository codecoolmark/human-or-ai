package at.codecool.humanoraiserver.services;

import at.codecool.humanoraiserver.model.User;

import java.util.Optional;

public class CreateUserResult {

    private final Optional<User> user;

    private final Optional<Boolean> isUsernameExists;

    private final Optional<Boolean> isEmailExists;

    public CreateUserResult(User user) {
        this.user = Optional.of(user);
        this.isEmailExists = Optional.empty();
        this.isUsernameExists = Optional.empty();
    }

    public CreateUserResult(boolean isUsernameExists, boolean isEmailExists) {
        this.user = Optional.empty();
        this.isEmailExists = Optional.of(isEmailExists);
        this.isUsernameExists = Optional.of(isUsernameExists);
    }

    public Optional<User> getUser() {
        return this.user;
    }

    public Optional<Boolean> getIsUsernameExists() {
        return this.isUsernameExists;
    }

    public Optional<Boolean> getIsEmailExists() {
        return this.isEmailExists;
    }
}
