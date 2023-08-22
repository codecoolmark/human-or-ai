package at.codecool.humanoraiserver.services;

import at.codecool.humanoraiserver.model.Quote;
import at.codecool.humanoraiserver.model.Vote;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import java.util.Optional;

public class VoteAndQuoteText {
    private final String text;
    private final boolean isReal;
    private final Instant created;
    private final Optional<Boolean> isCorrect;

    public VoteAndQuoteText(Vote vote, Quote quote, Instant now) {
        this.text = quote.getText();
        this.isReal = vote.getReal();
        this.created = vote.getCreated();

        if (quote.getExpires().isBefore(now)) {
            this.isCorrect = Optional.of(this.isReal == quote.isReal());
        } else {
            this.isCorrect = Optional.empty();
        }
    }

    public VoteAndQuoteText(Vote vote, Quote quote) {
        this(vote, quote, Instant.now());
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

    @JsonProperty("isCorrect")
    public Optional<Boolean> isCorrect() {
        return isCorrect;
    }
}
