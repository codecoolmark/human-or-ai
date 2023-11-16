import { Link } from "react-router-dom";
import * as api from "../../api";
import { useEffect, useState } from "react";
import { formatDateTime } from "../../formatters";
import { Quote } from "../../types";
import { useStore } from "../../store";
import { shallow } from "zustand/shallow";

export default function Quotes() {
    const [quotes, setQuotes] = useState<Quote[]>([]);
    const [setException] = useStore(
        (state) => [state.setException],
        shallow,
    );

    const loadQuotes = (setQuotes: (e: Quote[]) => void, setException: (e: Error) => void) => api.getQuotes().then((quotes) => setQuotes(quotes)).catch(setException);

    useEffect(() => {
        loadQuotes(setQuotes, setException)
    }, [setException]);

    const deleteQuote = (quoteId: number) => {
        api.deleteQuote(quoteId)
            .then(() => loadQuotes(setQuotes, setException));
    };

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
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        {quotes.map((quote, index) => (
                            <tr key={index}>
                                <td>{quote.text}</td>
                                {quote.isReal ? <td className="human center">Human</td> : <td className="ai center">Ai</td>}
                                <td>{formatDateTime(quote.expires)}</td>
                                <td><button type="button" onClick={() => deleteQuote(quote.id)}>Delete</button></td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </figure>
            <Link to="/quotes/new">Create new</Link>
        </main>
    );
}
