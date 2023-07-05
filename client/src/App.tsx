import { BrowserRouter, Link, Route, Routes } from "react-router-dom";
import LandingPage from "./pages/Landing";
import QuotesPage from "./pages/Quotes";
import LoginPage from "./pages/Login";
import RegisterPage from "./pages/Register";
import NewQuote from "./pages/NewQuote";
import Votes from "./pages/Votes";
import Navigation from "./components/Navigation";
import NotFound from "./pages/NotFound";
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
                            <LoggedIn>
                                <QuotesPage />
                            </LoggedIn>
                        }
                    />
                    <Route
                        path="/quotes/new"
                        element={
                            <LoggedIn>
                                <NewQuote />
                            </LoggedIn>
                        }
                    />
                    <Route
                        path="/votes"
                        element={
                            <LoggedIn>
                                <Votes />
                            </LoggedIn>
                        }
                    />
                    <Route path="*" Component={NotFound} />
                </Routes>
            </BrowserRouter>
        </>
    );
}

function LoggedIn({ children }: { children: React.ReactNode }) {
    const isLoggedIn = useStore(({ user }) => !!user);

    if (!isLoggedIn) {
        return (
            <p>
                Please <Link to="/login">login</Link> to view
            </p>
        );
    }

    return <>{children}</>;
}
