import { useState } from "react";
import { API_BASE } from "../../api";
import LoginForm, { LoginData } from "../../components/LoginForm";
import { UserData } from "../../types";

export default function RegisterPage() {
    const [registerError, setRegisterError] = useState<string | null>(null);
    const [registeredUser, setRegisteredUser] = useState<UserData | null>(null);

    const onRegister = async (login: LoginData) => {
        try {
            const user = await fetch(`${API_BASE}/users`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(login),
            }).then((res) => res.json());

            setRegisteredUser(user);
        } catch (err) {
            console.error(err);
            setRegisterError(err?.message || JSON.stringify(err));
        }
    };

    return (
        <main>
            {registerError && (
                <p className="error">An error occurred: {registerError}</p>
            )}

            {registeredUser && (
                <p className="success">
                    Successfully registered{" "}
                    <strong>{registeredUser.email}</strong>
                </p>
            )}

            <h1>Register Account</h1>

            <LoginForm
                onSubmit={onRegister}
                confirmPassword
                disabled={!!registeredUser}
            />
        </main>
    );
}
