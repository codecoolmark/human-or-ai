package at.codecool.humanoraiserver.controller;

import at.codecool.humanoraiserver.model.Quote;

public class PostQuoteResponse {

    public final String text;

    public final Long id;

    public PostQuoteResponse(Quote quote) {
        this.text = quote.getText();
        this.id = quote.getId();
    }

    public String getText() {
        return this.text;
    }

    public Long getId() {
        return this.id;
    }
}
