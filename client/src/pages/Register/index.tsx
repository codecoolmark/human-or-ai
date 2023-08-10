import { useState } from "react";
import { registerUser } from "../../api";
import RegisterForm from "./RegisterForm";
import { RegisterData } from "../../types";
import { useNavigate } from "react-router";
import { useStore } from "../../store";
import { shallow } from "zustand/shallow";

export default function RegisterPage() {
    const [disableRegistration, setDisableRegistration] = useState(false);
    const [usedEmail, setUsedEmail] = useState<string | null>(null);
    const [usedNickname, setUsedNickname] = useState<string | null>(null);
    const [setException] = useStore(
        (state) => [state.setException],
        shallow,
    );
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
            .catch(setException);
    };

    return (
        <main>
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
