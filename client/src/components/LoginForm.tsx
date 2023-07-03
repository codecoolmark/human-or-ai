import { useEffect, useState } from "react";

// https://emailregex.com/
const RE_EMAIL =
    /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

const RE_NICKNAME = /^.*(?=.{2,}).*$/;

const RE_PASSWORD =
    /^[a-zA-Z0-9!?.,;:\-_]*(?=.{6,})(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!?.,;:\-_])[a-zA-Z0-9!?.,;:\-_]*$/;

export interface LoginData {
    email: string;
    nickname: string;
    password: string;
}

interface LoginFormProps {
    onSubmit?: (login: LoginData) => void;
    confirmPassword?: boolean;
    disabled?: boolean;
}

export default function LoginForm({
    onSubmit: onSubmitProp,
    confirmPassword,
    disabled,
}: LoginFormProps) {
    const [email, setEmail] = useState("");
    const [nickname, setNickname] = useState("");
    const [password, setPassword] = useState("");
    const [passwordConfirm, setPasswordConfirm] = useState("");

    const [errors, setErrors] = useState({
        email: null as null | string,
        nickname: null as null | string,
        password: null as null | string,
        passwordConfirm: null as null | string,
    });

    const validateLogin = () => {
        setErrors((errors) => ({
            email:
                !email.length || RE_EMAIL.test(email)
                    ? null
                    : "Must be valid email format",
            nickname:
                !nickname.length || RE_NICKNAME.test(nickname)
                    ? null
                    : "Nickname must be at least 2 characters long",
            password:
                !password.length || RE_PASSWORD.test(password)
                    ? null
                    : "Password must be at least 6 characters long, contain 1 lower-case letter, 1 upper-case letter, 1 number, and 1 punctuation character",
            passwordConfirm:
                confirmPassword && passwordConfirm.length
                    ? password === passwordConfirm
                        ? null
                        : "Passwords don't match"
                    : null,
        }));
    };

    useEffect(validateLogin, [
        email,
        nickname,
        password,
        passwordConfirm,
        confirmPassword,
    ]);

    const isValid =
        [email, nickname, password].every((v) => v.length > 0) &&
        Object.values(errors).every((v) => !v);

    const onSubmit = (event: React.FormEvent) => {
        event.preventDefault();

        if (onSubmitProp && isValid) {
            onSubmitProp({ email, nickname, password });
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
                Nickname
                <input
                    type="text"
                    value={nickname}
                    onChange={(e) => setNickname(e.target.value)}
                    disabled={disabled}
                />
                {errors.nickname && (
                    <span className="error">{errors.nickname}</span>
                )}
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

            {confirmPassword && (
                <label>
                    Confirm Password
                    <input
                        type="password"
                        value={passwordConfirm}
                        onChange={(e) => setPasswordConfirm(e.target.value)}
                        disabled={disabled}
                    />
                    {errors.passwordConfirm && (
                        <span className="error">{errors.passwordConfirm}</span>
                    )}
                </label>
            )}

            <button type="submit" disabled={disabled || !isValid}>
                Register
            </button>
        </form>
    );
}
