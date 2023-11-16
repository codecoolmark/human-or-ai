package at.codecool.humanoraiserver.services;

import at.codecool.humanoraiserver.model.Quote;
import at.codecool.humanoraiserver.model.User;
import at.codecool.humanoraiserver.repositories.QuotesRepository;
import at.codecool.humanoraiserver.repositories.UsersRepository;
import at.codecool.humanoraiserver.repositories.VotesRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.Random;

@Service
public class QuotesService {
    private final QuotesRepository quotesRepository;

    private final UsersRepository usersRepository;

    private final VotesRepository votesRepository;

    public QuotesService(QuotesRepository quotesServices,
                         UsersRepository usersRepository,
                         VotesRepository votesRepository) {
        this.quotesRepository = quotesServices;
        this.usersRepository = usersRepository;
        this.votesRepository = votesRepository;
    }

    public Collection<Quote> getQuotes() {
        return quotesRepository.findAll();
    }

    public Quote createQuote(Quote newQuote) {
        quotesRepository.save(newQuote);
        return newQuote;
    }

    public Optional<Quote> nextQuoteForUser(User forUser) {
        var seed = forUser.getQuoteSeed();

        var randomGenerator = new Random(seed);
        var quotes = new ArrayList<>(quotesRepository.filterOpenQuotesForUser(forUser));
        if (quotes.isEmpty()) {
            return Optional.empty();
        }

        var index = randomGenerator.nextInt(quotes.size());
        var nextSeed = randomGenerator.nextLong();
        forUser.setQuoteSeed(nextSeed);
        usersRepository.save(forUser);

        return Optional.of(quotes.get(index));
    }

    @Transactional
    public void deleteQuoteById(Long quoteId) {
        var votes = this.votesRepository.findVotesByQuoteId(quoteId);
        this.votesRepository.deleteAll(votes);
        this.quotesRepository.deleteById(quoteId);
    }
}
