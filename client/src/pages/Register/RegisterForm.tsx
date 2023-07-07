import { useState } from "react";
import { RegisterData } from "../../types";
import { validateEmailAdress, validateNickname, validatePassword } from "../../validations";
import ValidatedInput from "../../components/ValidatedInput";

interface RegisterFormProps {
    onSubmit?: (data: RegisterData) => void;
    disabled: boolean;
}

export default function RegisterForm({ onSubmit: onSubmitProp, disabled }: RegisterFormProps) {
    const [email, setEmail] = useState(null as string | null);
    const [nickname, setNickname] = useState(null as string | null);
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

    return (
        <form action="#" onSubmit={onSubmit}>
            <label>
                Email
                <ValidatedInput
                    inputType="email"
                    onValidInput={setEmail}
                    validateInput={(email) => validateEmailAdress(email) ? null : "Must be valid email format"}
                    disabled={disabled}
                />
            </label>

            <label>
                Nickname
                <ValidatedInput
                    inputType="text"
                    onValidInput={setNickname}
                    validateInput={(nickname) => validateNickname(nickname) ? null : "Nickname must be at least 2 characters long"}
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

            <button type="submit" disabled={disabled || !isValid}>
                Register
            </button>
        </form>
    );
}
