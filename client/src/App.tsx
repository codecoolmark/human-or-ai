import { BrowserRouter, Route, Routes } from "react-router-dom";
import LandingPage from "./pages/Landing";

export default function App() {
    return (
        <>
            <BrowserRouter basename="/">
                <Routes>
                    <Route path="/" Component={LandingPage} />
                </Routes>
            </BrowserRouter>
        </>
    );
}
