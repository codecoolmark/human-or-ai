import { BrowserRouter, Route, Routes } from "react-router-dom";
import LandingPage from "./pages/Landing";
import QuotesPage from "./pages/quotes";
import LoginPage from "./pages/Login";
import RegisterPage from "./pages/Register";

export default function App() {
    return (
        <>
            <BrowserRouter basename="/">
                <Routes>
                    <Route path="/" Component={LandingPage} />
                    <Route path="/register" Component={RegisterPage} />
                    <Route path="/login" Component={LoginPage} />
                    <Route path="/quotes" Component={QuotesPage} />
                </Routes>
            </BrowserRouter>
        </>
    );
}
