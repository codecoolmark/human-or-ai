package at.codecool.humanoraiserver.services;

import at.codecool.humanoraiserver.model.Quote;
import at.codecool.humanoraiserver.model.Vote;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public class VoteAndQuoteText {
    private final String text;

    private final boolean isReal;

    private final Instant created;

    public VoteAndQuoteText(Vote vote, Quote quote) {
        this.text = quote.getText();
        this.isReal = vote.getReal();
        this.created = vote.getCreated();
    }

    public String getText() {
        return text;
    }

    @JsonProperty("isReal")
    public boolean isReal() {
        return isReal;
    }

    public Instant getCreated() {
        return created;
    }
}
