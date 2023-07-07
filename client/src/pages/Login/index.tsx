import { useState } from "react";
import { shallow } from "zustand/shallow";
import { loginUser } from "../../api";
import LoginForm from "./LoginForm";
import { useStore } from "../../store";
import { LoginData } from "../../types";
import { Link } from "react-router-dom";
import OnlyUser from "../../components/OnlyUser";

export default function LoginPage() {
    const [user, setUser] = useStore(
        (state) => [state.user, state.setUser],
        shallow,
    );

    const [disableLogin, setDisableLogin] = useState(false)

    const [error, setError] = useState<string | null>(null);

    const onLogin = async (login: LoginData) => {
        setDisableLogin(true)
        loginUser(login)
            .then(user => setUser(user))
            .catch(error => {
                setDisableLogin(false);
                console.error(error)
                setError("Login failed. Check your email address and password and try again.");
            })
    };

    return (
        <main>

            <OnlyUser>
                <p>
                    You are already logged in as <strong>{user?.email}</strong>
                </p>
            </OnlyUser>

            <h1>Login</h1>

            <LoginForm
                onSubmit={onLogin}
                disabled={disableLogin}
            />
            {error && <p className="error">{error}</p>}

            <p>No account? Create one <Link to="/register">here</Link></p>
        </main>
    );
}
