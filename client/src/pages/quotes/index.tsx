import { getQuotes} from "../../Api.tsx";
import {useEffect, useState} from "react";

export default function Quotes() {
    const [quotes, setQuotes] = useState([])

    useEffect(() => {
        getQuotes().then(quotes => setQuotes(quotes))
    }, [])

    return (
        <main>
            <h1>Quotes</h1>
            <ul>
                {quotes.map(quote => <li>{quote.text}</li>)}
            </ul>
        </main>
    );
}