package at.codecool.humanoraiserver.services;

import at.codecool.humanoraiserver.model.Quote;
import at.codecool.humanoraiserver.model.User;
import at.codecool.humanoraiserver.repositories.QuotesRepository;
import at.codecool.humanoraiserver.repositories.UsersRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.mockito.Mockito.*;

class QuotesServiceTest {

    @Test
    public void testNextQuoteForUser() {
        var quotesRepository = mock(QuotesRepository.class);
        var usersRepository = mock(UsersRepository.class);
        var quoteService = new QuotesService(quotesRepository, usersRepository);

        var random = new Random();
        var initialSeed = random.nextLong();
        var user = new User();
        user.setQuoteSeed(initialSeed);

        var quoteOptional = quoteService.nextQuoteForUser(user);

        Assertions.assertTrue(quoteOptional.isEmpty());

        var quote = new Quote();

        when(quotesRepository.filterOpenQuotesForUser(user)).thenReturn(List.of(quote));

        quoteOptional = quoteService.nextQuoteForUser(user);

        Assertions.assertEquals(Optional.of(quote), quoteOptional);
    }
}