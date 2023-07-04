import { useState } from "react";
import { loginUser } from "../../api";
import LoginForm from "../../components/LoginForm";
import { LoginData, UserData } from "../../types";

export default function LoginPage() {
    const [user, setUser] = useState<UserData | null>(null);
    const [error, setError] = useState<string | null>(null);

    const onLogin = async (login: LoginData) => {
        try {
            const user = await loginUser(login);
            if (user.isOk) {
                setUser(user.value);
            } else {
                setError(user.error);
            }
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
