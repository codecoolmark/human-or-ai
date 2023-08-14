package at.codecool.humanoraiserver.controller;

public class PostQuotesGenerateResponse {
    private final String quote;

    public PostQuotesGenerateResponse(String quote) {
        this.quote = quote;
    }

    public String getQuote() {
        return quote;
    }
}
