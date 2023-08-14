package at.codecool.humanoraiserver.services;

import at.codecool.humanoraiserver.model.Quote;
import at.codecool.humanoraiserver.model.Vote;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class VoteAndQuoteTextTest {

    private static Stream<Arguments> generateVoteQuoteInstant() {
        var random = new Random();
        var later = Instant.ofEpochSecond(random.nextLong(0, Instant.MAX.getEpochSecond()));
        var before = Instant.ofEpochSecond(random.nextLong(0, later.getEpochSecond()));

        var vote = new Vote();
        var nonExpiredQuote = new Quote();
        nonExpiredQuote.setExpires(later);

        var expiredQuote = new Quote();
        expiredQuote.setExpires(before);

        return Stream.of(Arguments.arguments(vote, nonExpiredQuote, before),
                Arguments.arguments(vote, expiredQuote, later));
    }

    public static Stream<Arguments> generateVoteAndExpiredQuote() {
        var now = Instant.now();
        var before = now.minus(Duration.ofSeconds(10));

        var quote = new Quote();
        quote.setExpires(before);
        var correctVote = new Vote();
        correctVote.setReal(quote.isReal());
        var incorrectVote = new Vote();
        incorrectVote.setReal(!quote.isReal());

        return Stream.of(Arguments.arguments(correctVote, quote, now),
                Arguments.arguments(incorrectVote, quote, now));
    }

    @ParameterizedTest
    @MethodSource("generateVoteQuoteInstant")
    public void testVisibilityOfIsCorrect(Vote vote, Quote quote, Instant now) {
        var voteAndQuoteText = new VoteAndQuoteText(vote, quote, now);

        assertEquals(quote.getExpires().isBefore(now), voteAndQuoteText.isCorrect().isPresent());
    }

    @ParameterizedTest
    @MethodSource("generateVoteAndExpiredQuote")
    public void testIsCorrect(Vote vote, Quote quote, Instant now) {
        var voteAndQuoteText = new VoteAndQuoteText(vote, quote, now);

        assertEquals(Optional.of(vote.getReal() == quote.isReal()), voteAndQuoteText.isCorrect());
    }
}