import { BrowserRouter, Route, Routes } from "react-router-dom";
import Navigation from "./components/Navigation";
import LandingPage from "./pages/Landing";
import LoginPage from "./pages/Login";
import NewQuote from "./pages/NewQuote";
import NotFound from "./pages/NotFound";
import QuotesPage from "./pages/Quotes";
import RegisterPage from "./pages/Register";
import VotePage from "./pages/Vote";
import VotesPage from "./pages/Votes";
import { useStore } from "./store";

export default function App() {
    return (
        <>
            <BrowserRouter basename="/">
                <Navigation />
                <Routes>
                    <Route path="/" Component={LandingPage} />
                    <Route path="/register" Component={RegisterPage} />
                    <Route path="/login" Component={LoginPage} />
                    <Route
                        path="/quotes"
                        element={
                            <CheckLogin
                                LoggedIn={QuotesPage}
                                LoggedOut={LoginPage}
                            />
                        }
                    />
                    <Route
                        path="/quotes/new"
                        element={
                            <CheckLogin
                                LoggedIn={NewQuote}
                                LoggedOut={LoginPage}
                            />
                        }
                    />
                    <Route
                        path="/vote"
                        element={
                            <CheckLogin
                                LoggedIn={VotePage}
                                LoggedOut={LoginPage}
                            />
                        }
                    />
                    <Route
                        path="/votes"
                        element={
                            <CheckLogin
                                LoggedIn={VotesPage}
                                LoggedOut={LoginPage}
                            />
                        }
                    />
                    <Route path="*" Component={NotFound} />
                </Routes>
            </BrowserRouter>
        </>
    );
}

function CheckLogin({
    LoggedIn,
    LoggedOut,
}: {
    LoggedIn: () => JSX.Element;
    LoggedOut: () => JSX.Element;
}) {
    const isLoggedIn = useStore(({ user }) => !!user);
    return isLoggedIn ? <LoggedIn /> : <LoggedOut />;
}
