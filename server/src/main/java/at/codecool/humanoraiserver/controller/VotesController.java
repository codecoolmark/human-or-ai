package at.codecool.humanoraiserver.controller;

import at.codecool.humanoraiserver.model.Vote;
import at.codecool.humanoraiserver.services.VotesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VotesController {
    private final VotesService votesService;

    public VotesController(VotesService voteService) {
        this.votesService = voteService;
    }

    @GetMapping("/votes")
    public Iterable<Vote> getVotes() {
        return this.votesService.getVotes();
    }

    @PostMapping("/votes")
    public Vote postVote(@RequestBody Vote vote) {
        return this.votesService.createVote(vote);
    }
}
