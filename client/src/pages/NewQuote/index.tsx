import { useState } from "react";
import { createQuote } from "../../api";
import { useNavigate } from "react-router";

function toISO(dateTime: string): string {
    const date = new Date(dateTime);
    return date.toISOString();
}

export default function NewQuote() {
    const navigate = useNavigate();

    const [text, setText] = useState<string | null>(null);
    const [isReal, setReal] = useState(false);
    const [expires, setExpires] = useState<string | null>(null);

    const onSubmit = (event: React.FormEvent) => {
        event.preventDefault();

        if (!text || !expires) {
            return;
        }

        createQuote({
            text,
            isReal,
            expires: toISO(expires),
        }).then(() => navigate("/quotes"));
    };

    const isValid = Boolean(text && expires);

    return (
        <main>
            <h1>Add a new quote</h1>
            <form onSubmit={onSubmit}>
                <label>
                    Text{" "}
                    <input
                        type="text"
                        maxLength={1024}
                        onChange={(event) => setText(event.target.value)}
                    />
                </label>
                <label>
                    Human{" "}
                    <input
                        type="checkbox"
                        onChange={(event) =>
                            setReal(event.target.value === "on")
                        }
                    />
                </label>
                <label>
                    Expires{" "}
                    <input
                        type="datetime-local"
                        onChange={(event) => setExpires(event.target.value)}
                    />
                </label>
                <button type="submit" disabled={!isValid}>
                    Create new quote
                </button>
            </form>
        </main>
    );
}
