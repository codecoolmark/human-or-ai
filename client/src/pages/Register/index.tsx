import { useState } from "react";
import { registerUser } from "../../api";
import LoginForm from "../../components/LoginForm";
import { LoginData, UserData } from "../../types";

export default function RegisterPage() {
    const [registerError, setRegisterError] = useState<string | null>(null);
    const [registeredUser, setRegisteredUser] = useState<UserData | null>(null);

    const onRegister = async (login: LoginData) => {
        try {
            const user = await registerUser(login);
            setRegisteredUser(user);
        } catch (err: any) {
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
                submitLabel="Register"
            />
        </main>
    );
}
