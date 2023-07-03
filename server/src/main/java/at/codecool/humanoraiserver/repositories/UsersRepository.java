package at.codecool.humanoraiserver.repositories;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import at.codecool.humanoraiserver.model.User;

@Repository
public interface UsersRepository extends CrudRepository<User, Long> {
    Collection<User> findAll();

    Optional<User> findByEmail(String email);

    User save(User user);
}
