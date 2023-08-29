import { useEffect, useState } from "react";
import * as api from "../../api";
import { Quote } from "../../types";
import QuoteContainer from "./QuoteContainer";
import { useStore } from "../../store";
import { shallow } from "zustand/shallow";

export default function Vote() {
    const [quote, setQuote] = useState<Quote | null>(null);
    const [setException] = useStore(
        (state) => [state.setException],
        shallow,
    );

    const fetchQuote = (setQuote: (quote: Quote) => void, setException: (e: Error) => void) => {
        api.quote()
            .then((newQuoteEither) => newQuoteEither.useA(quote => setQuote(quote)))
            .catch(setException);
    };

    useEffect(() => {
        if (quote === null) {
            fetchQuote(setQuote, setException);
        }
    }, [quote, setException]);

    const onHuman = (quote: Quote) => {
        api.createVote({
            quoteId: quote.id,
            isReal: true,
        }).then(() => fetchQuote(setQuote, setException)).catch(setException);
    };

    const onAi = (quote: Quote) => {
        api.createVote({
            quoteId: quote.id,
            isReal: false,
        }).then(() => fetchQuote(setQuote, setException)).catch(setException);
    };

    return (
        <main>
            <h1>Vote Now</h1>
            {quote !== null ? (
                <QuoteContainer
                    quote={quote}
                    onNextQuote={() => fetchQuote(setQuote, setException)}
                    onHuman={onHuman}
                    onAi={onAi}
                />
            ) : (
                <p>No quote available</p>
            )}
        </main>
    );
}
