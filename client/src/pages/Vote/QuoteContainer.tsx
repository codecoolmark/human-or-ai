export default function QuoteContainer({ quote, onNewQuote }) {
    return <div>
        <p>{quote.text}</p>
        <button type="button" onClick={() => onNewQuote()}>New quote</button>
    </div>
}