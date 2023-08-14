package at.codecool.humanoraiserver.controller;

import at.codecool.humanoraiserver.model.Quote;
import at.codecool.humanoraiserver.services.QuoteGenerator;
import at.codecool.humanoraiserver.services.QuotesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class QuotesController {

    private final QuotesService quotesService;
    private final QuoteGenerator quoteGenerator;

    public QuotesController(QuotesService quotesService, QuoteGenerator quoteGenerator) {
        this.quotesService = quotesService;
        this.quoteGenerator = quoteGenerator;
    }

    @GetMapping("/quotes")
    public Collection<Quote> getQuotes() {
        return quotesService.getQuotes();
    }

    @PostMapping("/quotes")
    public Quote postQuotes(@RequestBody Quote quote) {
        return quotesService.createQuote(quote);
    }

    @PostMapping("/quotes/generate")
    public PostQuotesGenerateResponse postQuoteGenerate() throws InterruptedException {
        return new PostQuotesGenerateResponse(this.quoteGenerator.generateQuote());
    }
}
