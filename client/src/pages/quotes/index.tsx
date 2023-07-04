import { getQuotes} from "../../Api.tsx";
import {useEffect, useState} from "react";

function formateDateTime(instant: string) {
    return new Intl.DateTimeFormat(navigator.language, {
        dateStyle: "full", timeStyle: "medium"
    }).format(new Date(instant))
}

export default function Quotes() {
    const [quotes, setQuotes] = useState([])

    useEffect(() => {
        getQuotes().then(quotes => setQuotes(quotes))
    }, [])

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
                    {quotes.map(quote => <tr>
                        <td>{quote.text}</td>
                        <td>{quote.real ? "Human" : "AI"}</td>
                        <td>{formateDateTime(quote.expires)}</td>
                    </tr>)}
                    </tbody>
                </table>
            </figure>
        </main>
    );
}