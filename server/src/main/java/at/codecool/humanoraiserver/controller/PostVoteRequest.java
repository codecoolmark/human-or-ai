package at.codecool.humanoraiserver.controller;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PostVoteRequest {
    private Long quoteId;

    private boolean isReal;

    public Long getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(Long quoteId) {
        this.quoteId = quoteId;
    }

    @JsonProperty("isReal")
    public boolean isReal() {
        return isReal;
    }

    @JsonProperty("isReal")
    public void setReal(boolean real) {
        isReal = real;
    }
}
