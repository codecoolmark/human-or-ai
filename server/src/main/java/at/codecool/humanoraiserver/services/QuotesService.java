package at.codecool.humanoraiserver.services;

import at.codecool.humanoraiserver.model.Quote;
import at.codecool.humanoraiserver.repositories.QuotesRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class QuotesService {
    private final QuotesRepository quotesRepository;

    public QuotesService(QuotesRepository newQuotesServices) {
        quotesRepository = newQuotesServices;
    }

    public Collection<Quote> getQuotes() {
        return quotesRepository.findAll();
    }
}
