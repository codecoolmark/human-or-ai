export default function QuoteContainer({ quote, onNewQuote, onHuman, onAi }) {
    return <div>
        <p>{quote.text}</p>
        <div>
            <button type="button" onClick={() => onHuman(quote)}>Human</button>
            <button type="button" onClick={() => onAi(quote)}>AI</button></div>
        <div>
            <button type="button" onClick={() => onNewQuote()}>New quote</button>
        </div>
    </div>
}