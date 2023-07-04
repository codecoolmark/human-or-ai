import { useEffect, useState } from "react";

// https://emailregex.com/
const RE_EMAIL =
    /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

const RE_NICKNAME = /^.*(?=.{2,}).*$/;

const RE_PASSWORD =
    /^[a-zA-Z0-9!?.,;:\-_]*(?=.{6,})(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!?.,;:\-_])[a-zA-Z0-9!?.,;:\-_]*$/;

export default function RegisterPage() {
    const [email, setEmail] = useState("");
    const [nickname, setNickname] = useState("");
    const [password, setPassword] = useState("");

    const [errors, setErrors] = useState({
        email: null as null | string,
        nickname: null as null | string,
        password: null as null | string,
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
        }));
    };

    useEffect(validateLogin, [email, nickname, password]);

    const onRegister = (event: React.FormEvent) => {
        event.preventDefault();

        console.log("register");
    };

    const isValid =
        [email, nickname, password].every((v) => v.length > 0) &&
        Object.values(errors).every((v) => !v);

    return (
        <main>
            <h1>Register Account</h1>

            <form action="#" onSubmit={onRegister}>
                <label>
                    Email
                    <input
                        type="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                    />
                    {errors.email && (
                        <span className="error">{errors.email}</span>
                    )}
                </label>

                <label>
                    Nickname
                    <input
                        type="text"
                        value={nickname}
                        onChange={(e) => setNickname(e.target.value)}
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
                    />
                    {errors.password && (
                        <span className="error">{errors.password}</span>
                    )}
                </label>

                <button type="submit" disabled={!isValid}>
                    Register
                </button>
            </form>
        </main>
    );
}
