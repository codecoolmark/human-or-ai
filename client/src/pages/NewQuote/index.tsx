import { useState } from "react";
import { createQuote } from "../../api";
import { useNavigate } from "react-router";
import { generateQuote } from "../../api";
import ValidatedInput from "../../components/ValidatedInput";

function toISO(dateTime: string): string {
    const date = new Date(dateTime);
    return date.toISOString();
}


function datetimeLocal(date: Date): string {
    const yearPart = date.getFullYear();
    const monthPart = (date.getMonth() + 1).toString().padStart(2, '0');
    const datePart = (date.getDate()).toString().padStart(2, '0');
    const hoursPart = date.getHours().toString().padStart(2, '0');
    const minutePart = date.getMinutes().toString().padStart(2, '0');

    return `${yearPart}-${monthPart}-${datePart}T${hoursPart}:${minutePart}`;
}

export default function NewQuote() {

    const navigate = useNavigate();

    const [text, setText] = useState<string>("");
    const [isReal, setReal] = useState<boolean>(true);
    const [expires, setExpires] = useState<string>(() => {
        const date = new Date();
        date.setDate(date.getDate() + 1);
        return datetimeLocal(date);
    });
    const [generateAiButtonDisabled, setGenerateAiButtonDisabled] = useState<boolean>(false);

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

    const onGenerateAiButtonOnClick = async () => {
        setGenerateAiButtonDisabled(true);
        const generatedQuote = await generateQuote();
        setText(generatedQuote.quote);
        setReal(false);
        setGenerateAiButtonDisabled(false);
    };

    const isValid = Boolean(text && expires);

    return (
        <main>
            <h1>Add a new quote</h1>
            <form onSubmit={onSubmit}>
                <label>
                    Text
                    <ValidatedInput 
                        inputType="textarea" 
                        onValidInput={setText} 
                        value={text}
                        validateInput={input => input !== "" ? null : "Please put in a quote."}
                        rows={5}/>
                </label>
                <div className="button-panel">
                    <button type="button" disabled={generateAiButtonDisabled} onClick={onGenerateAiButtonOnClick}>Generate AI Text</button>
                </div>
                <fieldset className="radio">
                    <label><span className="human">Human</span> 
                        <input
                        type="radio"
                        name="isReal"
                        value={"human"}
                        checked={isReal}
                        onChange={(event) => setReal(event.target.value === "human")}/>
                    </label>
                    <label><span className="ai">Ai</span> 
                        <input
                        type="radio"
                        name="isReal"
                        value={"ai"}
                        checked={!isReal}
                        onChange={(event) => setReal(event.target.value === "human")}/>
                    </label>
                    
                </fieldset>
                <label>
                    Expires
                    <ValidatedInput inputType="datetime-local" 
                        onValidInput={setExpires} 
                        value={expires}
                        validateInput={value => value !== "" ? null : "Please select when this quote expires"}/>
                </label>
                <button type="submit" disabled={!isValid}>
                    Create new quote
                </button>
            </form>
        </main>
    );
}
