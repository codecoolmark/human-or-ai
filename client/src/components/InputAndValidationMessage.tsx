import { useState } from "react";

interface InputAndValidationMessageProbs {
    inputType: string;
    disabled: boolean;
    validationMessage: string | null;
    processInput: (input: string) => void;
}

export default function InputAndValidationMessage({ inputType, disabled, validationMessage, processInput: processInput }: InputAndValidationMessageProbs) {
    const [showValidationMessage, setShowValidationMessage] = useState<boolean>(false)

    const onBlur = function(event: React.FocusEvent<HTMLInputElement>) {
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
    <input
        className="validated-input"
        type={inputType}
        disabled={disabled}
        onChange={(event) => processInput(event.target.value)}
        onBlur={onBlur}
    />
    {showValidationMessage && (validationMessage !== null) && (
        <span className="error-message">{validationMessage}</span>
    )}
</div>
}