import { useEffect, useState } from "react";
import * as api from "../../api";
import { Quote } from "../../types";
import QuoteContainer from "./QuoteContainer";

export default function Vote() {
    const [quote, setQuote] = useState<Quote | null>(null);

    const fetchQuote = () => {
        api.quote()
            .then((newQuote) => setQuote(newQuote))
            .catch(() => setQuote(null));
    };

    useEffect(() => {
        if (quote === null) {
            fetchQuote();
        }
    }, [quote]);

    const onHuman = (quote: Quote) => {
        api.createVote({
            quoteId: quote.id,
            isReal: true,
        }).then(() => fetchQuote());
    };

    const onAi = (quote: Quote) => {
        api.createVote({
            quoteId: quote.id,
            isReal: false,
        }).then(() => fetchQuote());
    };

    return (
        <main>
            <h1>Vote Now</h1>
            {quote !== null ? (
                <QuoteContainer
                    quote={quote}
                    onNewQuote={fetchQuote}
                    onHuman={onHuman}
                    onAi={onAi}
                />
            ) : (
                <p>No quote</p>
            )}
        </main>
    );
}
