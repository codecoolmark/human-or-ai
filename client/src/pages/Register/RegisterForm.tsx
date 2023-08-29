import { useState } from "react";
import { RegisterData } from "../../types";
import { validateEmailAdress, validateNickname, validatePassword } from "../../validations";
import ValidatedInput from "../../components/ValidatedInput";
import InputAndValidationMessage from "../../components/InputAndValidationMessage";

interface RegisterFormProps {
    onSubmit?: (data: RegisterData) => void;
    disabled: boolean;
    usedEmail: string | null;
    usedNickname: string | null;
}

export default function RegisterForm({ onSubmit: onSubmitProp, disabled, usedEmail, usedNickname }: RegisterFormProps) {
    const [email, setEmail] = useState(null as string | null);
    const [emailValidationMessage, setEmailValidationMessage] = useState<string | null>(null);
    const [nickname, setNickname] = useState(null as string | null);
    const [nicknameValidationMessage, setNicknameValidationMessage] = useState<string | null>(null);
    const [password, setPassword] = useState(null as string | null);
    const [passwordConfirm, setPasswordConfirm] = useState(null as string | null);

    const isValid = email !== null && nickname !== null && password !== null && passwordConfirm !== null;

    const onSubmit = (event: React.FormEvent) => {
        event.preventDefault();

        if (onSubmitProp && isValid) {
            onSubmitProp({
                nickname,
                password,
                email,
            });
        }
    };

    const emailUsedMessage = "This email address used by an existing user.";

    if (usedEmail != null && email === usedEmail && emailValidationMessage === null) {
        setEmailValidationMessage(emailUsedMessage);
    }

    const nickNameUsedMessage = "This nickname is used by an existing user."

    if (usedNickname != null && nickname === usedNickname && nicknameValidationMessage === null) {
        setNicknameValidationMessage(nickNameUsedMessage);
    }

    const validateEmailInput = (email: string) => {
        if (!validateEmailAdress(email)) {
            return "Must be valid email.";
        }
        if (usedEmail !== null && email === usedEmail) {
            return emailUsedMessage;
        }

        return null;
    };
    
    const onEmailChange = (input: string) => {
        const validationResult = validateEmailInput(input)
        setEmailValidationMessage(validationResult);
        if (validationResult === null) {
            setEmail(input);
        }
    }

    const validateNicknameInput = (nickname: string) => {
        if (!validateNickname(nickname)) {
            return "Nickname must be at least 2 characters long"
        }

        if (usedNickname !== null && nickname === usedNickname) {
            return nickNameUsedMessage;
        }

        return null;
    };

    const onNicknameChange = (input: string) => {
        const validationResult = validateNicknameInput(input);
        setNicknameValidationMessage(validationResult);
        if (validationResult === null) {
            setNickname(input);
        }
    }

    return (
        <form action="#" onSubmit={onSubmit}>
            <label>
                Email
                <InputAndValidationMessage
                    inputType="email"
                    processInput={onEmailChange}
                    validationMessage={emailValidationMessage}
                    disabled={disabled}
                />
            </label>

            <label>
                Nickname
                <InputAndValidationMessage
                    inputType="text"
                    processInput={onNicknameChange}
                    validationMessage={nicknameValidationMessage}
                    disabled={disabled}
                />
            </label>

            <label>
                Password
                <ValidatedInput
                    inputType="password"
                    onValidInput={setPassword}
                    validateInput={(password) => validatePassword(password) ? null : "Password must be at least 6 characters long, contain 1 lower-case letter, 1 upper-case letter, 1 number, and 1 punctuation character"}
                    disabled={disabled}
                />
            </label>

            <label>
                Confirm Password
                <ValidatedInput
                    inputType="password"
                    onValidInput={setPasswordConfirm}
                    validateInput={(passwordConfirm) => password === passwordConfirm ? null : "Passwords don't match"}
                    disabled={disabled}
                />
            </label>

            <div className="button-panel">
                <button type="submit" disabled={disabled || !isValid}>
                    Register
                </button>
            </div>
        </form>
    );
}
