import { useEffect, useState } from "react";
import * as api from "../../api";
import { useStore } from "../../store";
import { Quote } from "../../types";
import QuoteContainer from "./QuoteContainer";

export default function Vote() {
    const [quote, setQuote] = useState<Quote | null>(null);
    const userId = useStore((state) => state.user?.id);

    if (userId === undefined) {
        throw new Error("Expected user to be logged in");
    }

    const fetchQuote = () => {
        api.quote().then((newQuote) => setQuote(newQuote));
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
            userId,
        }).then(() => fetchQuote());
    };

    const onAi = (quote: Quote) => {
        api.createVote({
            quoteId: quote.id,
            isReal: false,
            userId,
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
                <></>
            )}
        </main>
    );
}

