package at.codecool.humanoraiserver.controller;

import at.codecool.humanoraiserver.model.Vote;
import at.codecool.humanoraiserver.repositories.QuotesRepository;
import at.codecool.humanoraiserver.repositories.UsersRepository;
import at.codecool.humanoraiserver.services.VotesService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VotesController {
    private final VotesService votesService;
    private final QuotesRepository quotesRepository;

    private final UsersRepository usersRepository;

    public VotesController(VotesService voteService, UsersRepository usersRepository, QuotesRepository quotesRepository) {
        this.votesService = voteService;
        this.usersRepository = usersRepository;
        this.quotesRepository = quotesRepository;
    }

    @GetMapping("/votes")
    public Iterable<Vote> getVotes() {
        return this.votesService.getVotes();
    }

    @PostMapping("/votes")
    public Vote postVote(@RequestBody PostVoteRequest voteRequest, Authentication authentication) {
        var quote = quotesRepository.findById(voteRequest.getQuoteId()).orElseThrow();
        var user = usersRepository.findByEmail(authentication.getName()).orElseThrow();
        return this.votesService.createVote(quote, user, voteRequest.isReal());
    }
}
