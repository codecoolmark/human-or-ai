import { useState } from "react";
import { LoginData } from "../../types";
import { validateEmailAdress } from "../../validations";
import ValidatedInput from "../../components/ValidatedInput";

interface LoginFormProps {
    onSubmit: (data: LoginData) => void;
    disabled: boolean;
}

export default function LoginForm({ onSubmit: onSubmitProp, disabled }: LoginFormProps) {
    const [email, setEmail] = useState(null as string | null);
    const [password, setPassword] = useState(null as string | null);


    const isValid = email !== null && password !== null;

    const onSubmit = (event: React.FormEvent) => {
        event.preventDefault();

        if (isValid) {
            onSubmitProp({
                password,
                userName: email,
            });
        }
    };

    return (
        <form action="#" onSubmit={onSubmit}>
            <label>
                Email
                <ValidatedInput
                    inputType="email"
                    onValidInput={setEmail}
                    validateInput={email => validateEmailAdress(email) ? null : "Please enter a correct email address."}
                    disabled={disabled}
                />
            </label>

            <label>
                Password
                <ValidatedInput
                    inputType="password"
                    onValidInput={setPassword}
                    validateInput={password => password.length > 5 ? null : "Please enter your password."}
                    disabled={disabled}
                />
            </label>

            <button type="submit" disabled={disabled || !isValid}>
                Login
            </button>
        </form>
    );
}
