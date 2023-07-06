import { useState } from "react";
import { shallow } from "zustand/shallow";
import { loginUser } from "../../api";
import LoginForm from "./LoginForm";
import { useStore } from "../../store";
import { LoginData } from "../../types";

export default function LoginPage() {
    const [user, setUser] = useStore(
        (state) => [state.user, state.setUser],
        shallow,
    );
    const [error, setError] = useState<string | null>(null);

    const onLogin = async (login: LoginData) => {
        try {
            const user = await loginUser(login);
            setUser(user);
        } catch (err: any) {
            console.error(err);
            setError(err?.message || JSON.stringify(err));
        }
    };

    return (
        <main>
            {error && <p className="error">An error occurred: {error}</p>}

            {user && (
                <p className="success">
                    Logged in as <strong>{user.email}</strong>
                </p>
            )}

            <h1>Login</h1>

            <LoginForm
                onSubmit={onLogin}
            />
        </main>
    );
}
