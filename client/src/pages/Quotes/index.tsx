import { Link } from "react-router-dom";
import { createVote, getQuotes } from "../../api";
import { useEffect, useMemo, useState } from "react";
import { formatDateTime } from "../../formatters";
import { Quote } from "../../types";

export default function QuotesPage() {
    const [quotes, setQuotes] = useState<Quote[]>([]);
    const [voted, setVoted] = useState<number[]>([]);


    useEffect(() => {
        getQuotes().then((quotes) => setQuotes(quotes));
    }, []);

    const guessQuote = (quoteId: number, isReal: boolean) => {
        createVote({ quoteId, isReal }).then((vote) =>
            setVoted((voted) => [...voted, vote.quoteId]),
        );
    };

    const votedSet = useMemo(() => new Set(voted), [voted]);

    return (
        <main>
            <h1>Quotes</h1>
            <figure>
                <table>
                    <thead>
                        <tr>
                            <th>Quote</th>
                            <th>Human or AI</th>
                            <th>Expires</th>
                            <th>Guess</th>
                        </tr>
                    </thead>
                    <tbody>
                        {quotes.map((quote, index) => (
                            <tr key={index}>
                                <td>{quote.text}</td>
                                <td>{quote.isReal ? "Human" : "AI"}</td>
                                <td>{formatDateTime(quote.expires)}</td>
                                <td>
                                    <button
                                        onClick={(e) =>
                                            guessQuote(quote.id, true)
                                        }
                                        disabled={votedSet.has(quote.id)}
                                    >
                                        Human
                                    </button>
                                    <button
                                        onClick={(e) =>
                                            guessQuote(quote.id, false)
                                        }
                                        disabled={votedSet.has(quote.id)}
                                    >
                                        AI
                                    </button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </figure>
            <Link to="/quotes/new">Create new</Link>
        </main>
    );
}
