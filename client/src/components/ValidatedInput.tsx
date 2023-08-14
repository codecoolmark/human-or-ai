import React, { useState } from "react";

interface ValidatedInputProps {
    inputType: string;
    onValidInput: (input: string) => void;
    validateInput: (input: string) => string | null;
    disabled?: boolean;
    rows?: number;
    value?: string
}

export default function ValidatedInput({
    inputType,
    onValidInput: onValidInput,
    validateInput,
    value = "",
    ...attributes
}: ValidatedInputProps) {
    const [previousExternalValue, setPreviousExternalValue] = useState<string>(value)
    const [inputValue, setInputValue] = useState<string>(value);
    const [showValidationMessage, setShowValidationMessage] = useState(false);
    const [validationMessage, setValidationMessage] = useState<string | null>(
        null,
    );

    if (value !== previousExternalValue) {
        setPreviousExternalValue(value);
        setInputValue(value);
    }

    const onChangeListener = (event: React.ChangeEvent<HTMLInputElement & HTMLTextAreaElement>) => {
        const input = event.target.value;
        const validationMessage = validateInput(input);
        setValidationMessage(validationMessage);
        setInputValue(input);

        if (validationMessage === null) {
            onValidInput(input);
        }
    };

    const onBlurListener = () => setShowValidationMessage(true);

    let inputElement = <input
        type={inputType}
        onChange={onChangeListener}
        onBlur={onBlurListener}
        value={inputValue}
        {...attributes}/>

    if (inputType === "textarea") {
        inputElement = <textarea
            onChange={onChangeListener}
            onBlur={onBlurListener}
            value={inputValue}
            {...attributes}></textarea>
    }

    return (
        <div>
            {inputElement}
            {showValidationMessage && (
                <span className="error">{validationMessage}</span>
            )}
        </div>
    );
}
