import { Link } from "react-router-dom";
import { getQuotes } from "../../api";
import { useEffect, useState } from "react";
import { Quote } from "../../types";

function formatDateTime(instant: string) {
    return new Intl.DateTimeFormat(navigator.language, {
        dateStyle: "full",
        timeStyle: "medium",
    }).format(new Date(instant));
}

export default function QuotesPage() {
    const [quotes, setQuotes] = useState<Quote[]>([]);

    useEffect(() => {
        getQuotes().then((quotes) => setQuotes(quotes));
    }, []);

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
                        </tr>
                    </thead>
                    <tbody>
                        {quotes.map((quote, index) => (
                            <tr key={index}>
                                <td>{quote.text}</td>
                                <td>{quote.isReal ? "Human" : "AI"}</td>
                                <td>{formatDateTime(quote.expires)}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </figure>
            <Link to="/quotes/new">Create new</Link>
        </main>
    );
}
