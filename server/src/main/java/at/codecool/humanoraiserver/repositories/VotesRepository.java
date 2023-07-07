package at.codecool.humanoraiserver.repositories;

import at.codecool.humanoraiserver.model.User;
import at.codecool.humanoraiserver.model.Vote;
import at.codecool.humanoraiserver.services.VoteAndQuoteText;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface VotesRepository extends CrudRepository<Vote, Long> {

    @Query("""
select new at.codecool.humanoraiserver.services.VoteAndQuoteText(v, q)
from Vote v join Quote q on v.quoteId = q.id
where v.userId = ?#{#user.getId()}
""")
    Collection<VoteAndQuoteText> filterVoteAndQuoteTextsForUser(User user);
}
