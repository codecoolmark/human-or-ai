import { Link } from "react-router-dom";
import {  getQuotes } from "../../api";
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

    useEffect(() => {
        getQuotes().then((quotes) => setQuotes(quotes)).catch(setException);
    }, [setException]);

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
                                <td>{quote.isReal ? <span className="human">Human</span> : <span className="ai">Ai</span>}</td>
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
