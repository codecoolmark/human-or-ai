package at.codecool.humanoraiserver.services;

import at.codecool.humanoraiserver.model.Vote;
import at.codecool.humanoraiserver.repositories.VotesRepository;

import java.time.Instant;

import org.springframework.stereotype.Service;

@Service
public class VotesService {
    private final VotesRepository voteRepository;

    public VotesService(VotesRepository newVoteRepository) {
        voteRepository = newVoteRepository;
    }

    public Iterable<Vote> getVotes() {
        return voteRepository.findAll();
    }

    public Vote createVote(Vote newVote) {
        newVote.setCreated(Instant.now());
        return this.voteRepository.save(newVote);
    }
}
