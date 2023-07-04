import { BrowserRouter, Route, Routes } from "react-router-dom";
import LandingPage from "./pages/Landing";
import Quotes from "./pages/quotes";

export default function App() {
    return (
        <>
            <BrowserRouter basename="/">
                <Routes>
                    <Route path="/" Component={LandingPage} />
                    <Route path="/quotes" Component={Quotes} />
                </Routes>
            </BrowserRouter>
        </>
    );
}
