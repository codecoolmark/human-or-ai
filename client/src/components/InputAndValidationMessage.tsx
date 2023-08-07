import { useState } from "react";

interface InputAndValidationMessageProbs {
    inputType: string;
    disabled: boolean;
    validationMessage: string | null;
    onChange: (input: string) => void;
}

export default function InputAndValidationMessage({ inputType, disabled, validationMessage, onChange }: InputAndValidationMessageProbs) {
    const [showValidationMessage, setShowValidationMessage] = useState<boolean>(false)

    const onBlur = function() {
        setShowValidationMessage(true);
    }

    return <div>
    <input
        type={inputType}
        disabled={disabled}
        onChange={(event) => onChange(event.target.value)}
        onBlur={onBlur}
    />
    {showValidationMessage && (validationMessage !== null) && (
        <span className="error">{validationMessage}</span>
    )}
</div>
}