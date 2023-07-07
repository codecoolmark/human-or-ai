import { RouterProvider, createBrowserRouter } from "react-router-dom";
import LandingPage from "./pages/Landing";
import LoginPage from "./pages/Login";
import NewQuote from "./pages/NewQuote";
import QuotesPage from "./pages/Quotes";
import RegisterPage from "./pages/Register";
import VotePage from "./pages/Vote";
import VotesPage from "./pages/Votes";
import { useStore } from "./store";
import { authorizeAdmin } from "./admins";
import Oops from "./pages/Oops";
import Layout from "./components/Layout";

export default function App() {
    const router = createBrowserRouter([{
        path: "/",
        element: <Layout></Layout>,
        errorElement: <Oops />,
        children: [
            {
                index: true,
                element: <LandingPage />
            },{
                path: "register",
                element: <RegisterPage />
            }, {
                path: "login",
                element: <LoginPage />
            }, {
                path: "quotes",
                element: <CheckLogin 
                    LoggedIn={
                        <AdminOnly>
                            <QuotesPage></QuotesPage>
                        </AdminOnly>} 
                    LoggedOut={<LoginPage />} />
            }, {
                path: "quotes/new",
                element: <CheckLogin 
                    LoggedIn={
                        <AdminOnly>
                            <NewQuote></NewQuote>
                        </AdminOnly>} 
                    LoggedOut={<LoginPage />} />
            }, {
                path: "vote",
                element: <CheckLogin
                    LoggedIn={<VotePage />}
                    LoggedOut={<LoginPage />} />
            }, {
                path: "votes",
                element: <CheckLogin
                    LoggedIn={<VotesPage />}
                    LoggedOut={<LoginPage />}
                />
            }]
    }])

    return <RouterProvider router={router}/>
}

function CheckLogin({ LoggedIn, LoggedOut}: { LoggedIn: JSX.Element; LoggedOut: JSX.Element; }) {
    const isLoggedIn = useStore(({ user }) => user !== null);

    return isLoggedIn ? LoggedIn : LoggedOut;
}

function AdminOnly({ children }: { children: JSX.Element }) {
    const user = useStore((state) => state.user);
    authorizeAdmin(user);

    return children;
}