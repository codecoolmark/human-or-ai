import { BrowserRouter, Route, Routes } from "react-router-dom";
import LandingPage from "./pages/Landing";
import QuotesPage from "./pages/Quotes";
import LoginPage from "./pages/Login";
import RegisterPage from "./pages/Register";
import NewQuote from "./pages/NewQuote";
import Votes from "./pages/Votes";

export default function App() {
    return (
        <>
            <BrowserRouter basename="/">
                <Routes>
                    <Route path="/" Component={LandingPage} />
                    <Route path="/register" Component={RegisterPage} />
                    <Route path="/login" Component={LoginPage} />
                    <Route path="/quotes" Component={QuotesPage} />
                    <Route path="/quotes/new" Component={NewQuote} />
                    <Route path="/votes" Component={Votes} />
                </Routes>
            </BrowserRouter>
        </>
    );
}
