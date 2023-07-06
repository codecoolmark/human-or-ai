package at.codecool.humanoraiserver.controller;

import at.codecool.humanoraiserver.model.Quote;

public class PostQuoteResponse {

    public final String text;

    public PostQuoteResponse(Quote quote) {
        this.text = quote.getText();
    }

    public String getText() {
        return this.text;
    }
}
