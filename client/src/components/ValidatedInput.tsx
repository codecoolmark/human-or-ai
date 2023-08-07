import { useState } from "react";
import InputAndValidationMessage from "./InputAndValidationMessage";

interface ValidatedInputProps {
    inputType: string;
    onValidInput: (input: string) => void;
    validateInput: (input: string) => string | null;
    disabled: boolean;
}

export default function ValidatedInput({
    inputType,
    onValidInput: onInput,
    validateInput,
    disabled,
}: ValidatedInputProps) {
    const [validationMessage, setValidationMessage] = useState<string | null>(
        null,
    );
    const [input, setInput] = useState<string>("");

    const validate = () => {
        const validationMessage = validateInput(input);
        setValidationMessage(validationMessage);
        if (validationMessage === null) {
            onInput(input);
        }
    }

    const onChangeListener = (input: string) => {
        setInput(input);
        validate();
    };

    return <InputAndValidationMessage 
        inputType={inputType} 
        disabled={disabled} 
        onChange={onChangeListener} 
        validationMessage={validationMessage} />
}
