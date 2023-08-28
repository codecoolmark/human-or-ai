import { useState } from "react";
import InputAndValidationMessage from "./InputAndValidationMessage";

interface ValidatedInputProps {
    inputType: string;
    onValidInput: (input: string) => void;
    validateInput: (input: string) => string | null;
    disabled?: boolean;
    rows?: number;
    overwriteValue?: string
}

export default function ValidatedInput({
    inputType,
    onValidInput: onValidInput,
    validateInput,
    overwriteValue,
    disabled = false,
    ...attributes
}: ValidatedInputProps) {
    
    const [previousOverwriteValue, setPreviousOverwriteValue] = useState<string | undefined>(overwriteValue);
    const [inputValue, setInputValue] = useState<string>(overwriteValue ?? "");
    const [validationMessage, setValidationMessage] = useState<string | null>(
        null,
    );

    if (previousOverwriteValue !== overwriteValue) {
        setPreviousOverwriteValue(overwriteValue);

        if (overwriteValue !== undefined) {
            setInputValue(overwriteValue);
        }
    }

    const validate = () => {
        const validationMessage = validateInput(inputValue);
        setValidationMessage(validationMessage);
        if (validationMessage === null) {
            onValidInput(inputValue);
        }
    }

    const onChangeListener = (input: string) => {
        setInputValue(input);
        validate();
    };

    return <InputAndValidationMessage 
        inputType={inputType} 
        disabled={disabled}
        value={inputValue} 
        processInput={onChangeListener} 
        validationMessage={validationMessage}
        {...attributes} />
}
