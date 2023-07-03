import LoginForm, { LoginData } from "../../components/LoginForm";

export default function RegisterPage() {
    const onRegister = (login: LoginData) => {
        console.log("register");
    };

    return (
        <main>
            <h1>Register Account</h1>

            <LoginForm onSubmit={onRegister} confirmPassword />
        </main>
    );
}
