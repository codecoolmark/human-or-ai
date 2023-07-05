package at.codecool.humanoraiserver.repositories;

import at.codecool.humanoraiserver.model.Vote;
import org.springframework.data.repository.CrudRepository;

public interface VotesRepository extends CrudRepository<Vote, Long> {
}
