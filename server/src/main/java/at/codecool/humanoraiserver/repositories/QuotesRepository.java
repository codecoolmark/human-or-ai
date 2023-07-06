package at.codecool.humanoraiserver.repositories;

import at.codecool.humanoraiserver.model.Quote;
import at.codecool.humanoraiserver.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface QuotesRepository extends CrudRepository<Quote, Long> {
    Collection<Quote> findAll();
    Quote save(Quote newQuote);

    @Query(value = """
with quote_ids_for_user (quote_id) as(
    select quote.id as quote_id from quote
        join vote on quote.id = vote.quote_id
        join "user" on vote.user_id = "user".id
    where "user".id = ?#{#user.getId()})

select * from quote where quote.id not in (select quote_id from quote_ids_for_user)
""", nativeQuery = true)
    Collection<Quote> filterOpenQuotesForUser(@Param("user") User user);
}
