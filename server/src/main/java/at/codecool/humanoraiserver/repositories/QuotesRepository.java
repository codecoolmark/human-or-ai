package at.codecool.humanoraiserver.repositories;

import at.codecool.humanoraiserver.model.Quote;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public class QuotesRepository {

    public Collection<Quote> filterAllQuotes() {
        return List.of(new Quote("Aaron or Erdogan"), new Quote("Alireza is haralding as usual no surpirse here"));
    }
}
