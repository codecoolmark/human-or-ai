import { useState } from "react";

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
    const [showValidationMessage, setShowValidationMessage] = useState(false);
    const [validationMessage, setValidationMessage] = useState<string | null>(
        null,
    );

    const onInputListener = (event: React.ChangeEvent<HTMLInputElement>) => {
        const input = event.target.value;
        const validationMessage = validateInput(input);
        setValidationMessage(validationMessage);

        if (validationMessage === null) {
            onInput(input);
        }
    };

    const onBlurListener = () => setShowValidationMessage(true);

    return (
        <div>
            <input
                type={inputType}
                disabled={disabled}
                onInput={onInputListener}
                onBlur={onBlurListener}
            />
            {showValidationMessage && (
                <span className="error">{validationMessage}</span>
            )}
        </div>
    );
}
