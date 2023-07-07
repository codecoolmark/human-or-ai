import { useState } from "react";
import { shallow } from "zustand/shallow";
import { loginUser } from "../../api";
import LoginForm from "../../components/LoginForm";
import { useStore } from "../../store";
import { LoginData } from "../../types";

export default function LoginPage() {
    const [user, setUser] = useStore(
        (state) => [state.user, state.setUser],
        shallow,
    );
    const [error, setError] = useState<string | null>(null);

    const onLogin = async (login: LoginData) => {
        loginUser(login).then(user => setUser(user)).catch(error => setError(error))
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
