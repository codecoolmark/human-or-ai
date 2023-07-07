import { useState } from "react";
import { registerUser } from "../../api";
import RegisterForm from "./RegisterForm";
import { RegisterData } from "../../types";
import { useNavigate } from "react-router";

export default function RegisterPage() {
    const [registerError, setRegisterError] = useState<string | null>(null);
    const [disableRegistration, setDisableRegistration] = useState(false);
    const navigate = useNavigate();

    const onRegister = async (data: RegisterData) => {
        setDisableRegistration(true)
        registerUser(data)
            .then(registerResult => {
                if (registerResult.isOk) {
                    navigate("/")
                } else {
                    setRegisterError(registerResult.error);
                    setDisableRegistration(false);
                }
            })
            .catch(error => {
                console.error(error);
                setRegisterError("Couldn't create account. Please try again later.")
            })
    };

    return (
        <main>
            {registerError && (
                <p className="error">{registerError}</p>
            )}

            <h1>Register Account</h1>

            <RegisterForm
                onSubmit={onRegister}
                disabled={disableRegistration}
            />
        </main>
    );
}
