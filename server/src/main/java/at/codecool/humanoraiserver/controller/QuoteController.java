package at.codecool.humanoraiserver.controller;

import at.codecool.humanoraiserver.quotegenerator.QuoteGenerator;
import at.codecool.humanoraiserver.repositories.UsersRepository;
import at.codecool.humanoraiserver.services.QuotesService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class QuoteController {

    private final UsersRepository usersRepository;

    private final QuotesService quotesService;

    public QuoteController(UsersRepository usersRepository, QuotesService quoteService) {
        this.usersRepository = usersRepository;
        this.quotesService = quoteService;
    }

    @PostMapping ("/quote")
    public PostQuoteResponse postQuote(Authentication authentication) {
        var user = usersRepository.findByEmail(authentication.getName());
        var quoteOpt = quotesService.nextQuoteForUser(user.orElseThrow());
        if (quoteOpt.isEmpty()) {
            return null;
        }

        return new PostQuoteResponse(quoteOpt.get());
    }
}
