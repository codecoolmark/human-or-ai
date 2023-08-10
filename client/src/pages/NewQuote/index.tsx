import { useState } from "react";
import { createQuote } from "../../api";
import { useNavigate } from "react-router";
import { useStore } from "../../store";
import { shallow } from "zustand/shallow";

function toISO(dateTime: string): string {
    const date = new Date(dateTime);
    return date.toISOString();
}

export default function NewQuote() {

    const navigate = useNavigate();

    const [text, setText] = useState("");
    const [isReal, setReal] = useState(false);
    const [expires, setExpires] = useState(() => getTomorrow());
    const [setException] = useStore(
        (state) => [state.setException],
        shallow,
    );

    const onSubmit = (event: React.FormEvent) => {
        event.preventDefault();

        if (!text || !expires) {
            return;
        }

        createQuote({
            text,
            isReal,
            expires: toISO(expires),
        }).then(() => navigate("/quotes")).catch(setException);
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
                        value={text}
                        onChange={(event) => setText(event.target.value)}
                    />
                </label>
                <label>
                    Human{" "}
                    <input
                        type="checkbox"
                        checked={isReal}
                        onChange={(event) => setReal(event.target.checked)}
                    />
                </label>
                <label>
                    Expires{" "}
                    <input
                        type="datetime-local"
                        value={expires}
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

function getTomorrow(): string {
    const now = new Date();
    now.setDate(now.getDate() + 1);
    return now
        .toISOString()
        .replace(/T\d+:\d+:\d+/, "T00:00:00")
        .replace(/\.\d+Z$/, "");
}
