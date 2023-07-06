import { Quote } from "../../types";

interface QuoteContainerProps {
    quote: Quote;
    onNewQuote: () => void;
    onHuman: (quote: Quote) => void;
    onAi: (quote: Quote) => void;
}

export default function QuoteContainer({
    quote,
    onNewQuote,
    onHuman,
    onAi,
}: QuoteContainerProps) {
    return (
        <div>
            <p>{quote.text}</p>
            <div>
                <button type="button" onClick={() => onHuman(quote)}>
                    Human
                </button>
                <button type="button" onClick={() => onAi(quote)}>
                    AI
                </button>
            </div>
            <div>
                <button type="button" onClick={() => onNewQuote()}>
                    New quote
                </button>
            </div>
        </div>
    );
}

