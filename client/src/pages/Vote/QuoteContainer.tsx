import { Quote } from "../../types";

interface QuoteContainerProps {
    quote: Quote;
    onNextQuote: () => void;
    onHuman: (quote: Quote) => void;
    onAi: (quote: Quote) => void;
}

export default function QuoteContainer({
    quote,
    onNextQuote: onNextQuote,
    onHuman,
    onAi,
}: QuoteContainerProps) {
    return (
        <div>
            <p>Make up your mind is this qoute</p>
            <p className="quote">{quote.text}</p>
            <div className="decision-panel">
                <button type="button" className="human" onClick={() => onHuman(quote)}>
                    Human
                </button>
                <p>Human or AI generated?</p>
                <button type="button" className="ai" onClick={() => onAi(quote)}>
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

