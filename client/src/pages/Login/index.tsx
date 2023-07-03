import { useState } from "react";
import { API_BASE } from "../../api";
import LoginForm, { LoginData } from "../../components/LoginForm";
import { UserData } from "../../types";

export default function LoginPage() {
    const [user, setUser] = useState<UserData | null>(null);
    const [error, setError] = useState<string | null>(null);

    const onLogin = async (login: LoginData) => {
        try {
            const res = await fetch(`${API_BASE}/users/login`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(login),
            });

            if (!res.ok) {
                const msg =
                    res.status === 403
                        ? "Invalid login data"
                        : res.status.toString();
                setError(msg);
                return;
            }

            const user = await res.json();
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
                disabled={!!user}
                submitLabel="Login"
                omitNickname
            />
        </main>
    );
}
