package at.codecool.humanoraiserver.services;

import at.codecool.humanoraiserver.model.Quote;
import at.codecool.humanoraiserver.model.User;
import at.codecool.humanoraiserver.model.Vote;
import at.codecool.humanoraiserver.repositories.VotesRepository;

import java.time.Instant;
import java.util.Collection;

import org.springframework.stereotype.Service;

@Service
public class VotesService {
    private final VotesRepository voteRepository;

    public VotesService(VotesRepository newVoteRepository) {
        voteRepository = newVoteRepository;
    }

    public Collection<VoteAndQuoteText> getVotesForUser(User user) {
        return voteRepository.findVoteAndQuoteTextsForUser(user);
    }

    public Vote createVote(Quote quote, User forUser, boolean isReal) {
        var vote = new Vote();
        vote.setUserId(forUser.getId());
        vote.setQuoteId(quote.getId());
        vote.setReal(isReal);
        vote.setCreated(Instant.now());

        return this.voteRepository.save(vote);
    }
}
