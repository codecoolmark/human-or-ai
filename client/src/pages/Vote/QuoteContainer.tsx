import { useEffect } from "react";
import { Quote } from "../../types";

interface QuoteContainerProps {
    quote: Quote;
    onNextQuote: () => void;
    onHuman: (quote: Quote) => void;
    onAi: (quote: Quote) => void;
}

export default function QuoteContainer({
    quote,
    onNextQuote,
    onHuman,
    onAi,
}: QuoteContainerProps) {
    // eslint-disable-next-line react-hooks/exhaustive-deps
    const shortcuts = new Map([
        ["PageUp", () => onHuman(quote)],
        ["PageDown", () => onAi(quote)],
        ["b", onNextQuote],
    ]);

    useEffect(() => {
        const onKey = (e: KeyboardEvent) => {
            const key = e.key;
            const handler = shortcuts.get(key);
            if (handler) {
                handler();
            }
        };

        document.addEventListener("keydown", onKey);

        return () => document.removeEventListener("keydown", onKey);
    }, [shortcuts]);

    return (
        <div>
            <p>Make up your mind is this qoute</p>
            <p className="quote">{quote.text}</p>
            <div className="decision-panel">
                <button
                    type="button"
                    className="human"
                    onClick={() => onHuman(quote)}
                >
                    Human
                </button>
                <p>Human or AI generated?</p>
                <button
                    type="button"
                    className="ai"
                    onClick={() => onAi(quote)}
                >
                    AI
                </button>
            </div>
            <div className="next-quote-panel">
                <button type="button" onClick={() => onNextQuote()}>
                    Next quote
                </button>
            </div>
        </div>
    );
}
