import { BrowserRouter, Route, Routes } from "react-router-dom";
import LandingPage from "./pages/Landing";
import QuotesPage from "./pages/Quotes";
import LoginPage from "./pages/Login";
import RegisterPage from "./pages/Register";
import NewQuote from "./pages/NewQuote";
import Votes from "./pages/Votes";
import Navigation from "./components/Navigation";
import NotFound from "./pages/NotFound";
import { useStore } from "./store";
import Vote from "./pages/Vote";

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
                            <CheckLogin LoggedIn={QuotesPage} LoggedOut={LoginPage}/>
                        }
                    />
                    <Route
                        path="/quotes/new"
                        element={
                            <CheckLogin LoggedIn={NewQuote} LoggedOut={LoginPage} />
                        }
                    />
                    <Route
                        path="/vote"
                        element={
                            <CheckLogin LoggedIn={Vote} LoggedOut={LoginPage} />
                        }
                    />
                    <Route
                        path="/votes"
                        element={
                            <CheckLogin LoggedIn={Votes} LoggedOut={LoginPage}/>
                        }
                    />
                    <Route path="*" Component={NotFound} />
                </Routes>
            </BrowserRouter>
        </>
    );
}

function CheckLogin({ LoggedIn, LoggedOut }: { LoggedIn: () => JSX.Element, LoggedOut: () => JSX.Element }) {
    const isLoggedIn = useStore(({ user }) => !!user);

    if (!isLoggedIn) {
        return <LoggedOut></LoggedOut>
    }

    return <><LoggedIn></LoggedIn></>;
}
