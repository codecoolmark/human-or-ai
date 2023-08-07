import { useState } from "react";
import { registerUser } from "../../api";
import RegisterForm from "./RegisterForm";
import { RegisterData } from "../../types";
import { useNavigate } from "react-router";

export default function RegisterPage() {
    const [registerError, setRegisterError] = useState<string | null>(null);
    const [disableRegistration, setDisableRegistration] = useState(false);
    const [usedEmail, setUsedEmail] = useState<string | null>(null);
    const [usedNickname, setUsedNickname] = useState<string | null>(null);
    const navigate = useNavigate();

    const onRegister = async (data: RegisterData) => {
        setDisableRegistration(true)
        registerUser(data)
            .then((response) => {
                setDisableRegistration(false);
                if (response.isUsernameExists === true) {
                    setUsedNickname(data.nickname);
                } else if (response.isEmailExists === true) {
                    setUsedEmail(data.email);
                } else {
                    navigate("/");
                }
                
            })
            .catch(error => {
                console.error(error);
                setRegisterError("Couldn't create account. Please try again later.")
            });
    };

    return (
        <main>
            {registerError && (
                <p className="error">{registerError}</p>
            )}

            <h1>Register a new Account</h1>

            <RegisterForm
                onSubmit={onRegister}
                disabled={disableRegistration}
                usedEmail={usedEmail}
                usedNickname={usedNickname}
            />
        </main>
    );
}
