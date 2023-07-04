package at.codecool.humanoraiserver.services;

import at.codecool.humanoraiserver.model.Vote;
import at.codecool.humanoraiserver.repositories.VoteRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class VoteService {
    private final VoteRepository voteRepository;

    public VoteService(VoteRepository newVoteRepository) {
        voteRepository = newVoteRepository;
    }

    public Iterable<Vote> getVotes() {
        return voteRepository.findAll();
    }
}
