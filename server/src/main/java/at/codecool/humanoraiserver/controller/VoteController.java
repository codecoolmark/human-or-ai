package at.codecool.humanoraiserver.controller;

import at.codecool.humanoraiserver.model.Vote;
import at.codecool.humanoraiserver.services.VoteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class VoteController {
    private final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @GetMapping("/votes")
    public Iterable<Vote> getVotes() {
        return this.voteService.getVotes();
    }
}
