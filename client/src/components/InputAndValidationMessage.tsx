import { useState } from "react";

interface InputAndValidationMessageProbs {
    inputType: string;
    disabled: boolean;
    validationMessage: string | null;
    processInput: (input: string) => void;
    value?: string;
}

export default function InputAndValidationMessage({ 
        inputType, 
        disabled, 
        validationMessage, 
        processInput, 
        value,
        ...attributes
    }: InputAndValidationMessageProbs) {
    
    const [showValidationMessage, setShowValidationMessage] = useState<boolean>(false)

    const onBlur = function(event: {target: { value: string } }) {
        processInput(event.target.value);
        setShowValidationMessage(true);
    }

    let cssClass = "unvalidated";

    if (showValidationMessage && validationMessage === null) {
        cssClass = "valid";
    } else if (showValidationMessage) {
        cssClass = "error";
    }

    return <div className={"input-container " + cssClass}>
        {inputType === "textarea" ? 
            <textarea className="validated-input"
            disabled={disabled}
            onChange={(event) => processInput(event.target.value)}
            onBlur={onBlur}
            value={value}
            {...attributes}></textarea> 
        :
            <input
                className="validated-input"
                type={inputType}
                disabled={disabled}
                onChange={(event) => processInput(event.target.value)}
                onBlur={onBlur}
                value={value}
                {...attributes} />
        }

    {showValidationMessage && (validationMessage !== null) && (
        <span className="error-message">{validationMessage}</span>
    )}
</div>
}