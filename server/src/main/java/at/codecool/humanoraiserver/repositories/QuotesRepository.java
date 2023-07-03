package at.codecool.humanoraiserver.repositories;

import at.codecool.humanoraiserver.model.Quote;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface QuotesRepository extends CrudRepository<Quote, Long> {
    Collection<Quote> findAll();
}
