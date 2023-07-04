import { useState } from "react"
import { createQuote } from "../../api";
import { useNavigate } from "react-router";

function toISO(dateTime: string | null): string | null {
    if (dateTime) {
        const date = new Date(dateTime);
        return date.toISOString();
    }
    return null;
}

export default function NewQuote() {
    const navigate = useNavigate()

    const [text, setText] = useState(null);
    const [real, setReal] = useState(false);
    const [expires, setExpires] = useState(null);

    const onSubmit = (event) => {
        event.preventDefault();

        createQuote({
            text, real, expires: toISO(expires)
        }).then(() => navigate("/quotes"));
    }

    return <main>
        <h1>Add a new quote</h1>
        <form onSubmit={onSubmit}>
            <label>
                Text <input type="text" maxLength={1024} onChange={event => setText(event.target.value)} />
            </label>
            <label>
                Human <input type="checkbox" onChange={event => setReal(event.target.value === "on")}/>
            </label>
            <label>
                Expires <input type="datetime-local" onChange={event => setExpires(event.target.value)} />
            </label>
            <button type="submit">Create new quote</button>
        </form>
    </main>
}