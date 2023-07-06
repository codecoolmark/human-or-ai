import { useEffect, useState } from "react";
import { LoginData } from "../../types";

// https://emailregex.com/
const RE_EMAIL =
    /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

const RE_PASSWORD =
    /^[a-zA-Z0-9!?.,;:\-_]*(?=.{6,})(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!?.,;:\-_])[a-zA-Z0-9!?.,;:\-_]*$/;

interface LoginFormProps {
    onSubmit?: (login: LoginData) => void;
}

export default function LoginForm({ onSubmit: onSubmitProp }: LoginFormProps) {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [disabled, setDisabled] = useState(false);

    const [errors, setErrors] = useState({
        email: null as null | string,
        password: null as null | string,
    });

    const validateLogin = () => {
        setErrors(() => ({
            email:
                !email.length || RE_EMAIL.test(email)
                    ? null
                    : "Must be valid email format",
            password:
                !password.length || RE_PASSWORD.test(password)
                    ? null
                    : "Password must be at least 6 characters long, contain 1 lower-case letter, 1 upper-case letter, 1 number, and 1 punctuation character"
        }));
    };

    useEffect(validateLogin, [email, password]);

    const isValid =
        email.length > 0 &&
        password.length > 0 &&
        Object.values(errors).every((v) => !v);

    const onSubmit = (event: React.FormEvent) => {
        event.preventDefault();

        if (onSubmitProp && isValid) {
            onSubmitProp({ userName: email, password });
        }
    };

    return (
        <form action="#" onSubmit={onSubmit}>
            <label>
                Email
                <input
                    type="email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    disabled={disabled}
                />
                {errors.email && <span className="error">{errors.email}</span>}
            </label>

            <label>
                Password
                <input
                    type="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    disabled={disabled}
                />
                {errors.password && (
                    <span className="error">{errors.password}</span>
                )}
            </label>

            <button type="submit" disabled={disabled || !isValid}>
                Login
            </button>
        </form>
    );
}
