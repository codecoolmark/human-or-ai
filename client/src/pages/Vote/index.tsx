import { useState } from "react"
import * as api from "../../api";
import QuoteContainer from "./QuoteContainer";

export default function Vote() {
    const [quote, setQuote] = useState(null);

    const fetchQuote = () => {
        api.quote().then(newQuote => setQuote(newQuote));
    }

    if (quote === null) {
        fetchQuote();
    }

    const onHuman = (quote) => {
        api.createVote({ quoteId: quote.id, isReal: true }).then(() => fetchQuote());
    }

    const onAi = (quote) => {
        api.createVote({ quoteId: quote.id, isReal: false }).then(() => fetchQuote());
    }

    return <main>
        <h1>Vote Now</h1>
        {quote !== null ? <QuoteContainer quote={quote} onNewQuote={fetchQuote} onHuman={onHuman} onAi={onAi} /> : <></>}
    </main>
}