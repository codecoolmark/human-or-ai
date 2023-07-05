import { Link } from "react-router-dom";
import { logoutUser } from "../api";
import { useStore } from "../store";

export default function Navigation() {
    const user = useStore((state) => state.user);
    const clearUser = useStore((state) => state.clearUser);

    const onLogout = () => {
        clearUser();
        logoutUser().then(console.log);
    };

    return (
        <nav>
            <Link to="/">Home</Link>
            {user ? (
                <>
                    <Link to="/votes">Votes</Link>
                    <Link to="/quotes">Quotes</Link>
                    <Link to="/quotes/new">New Quote</Link>
                    <button onClick={onLogout}>Logout</button>
                </>
            ) : (
                <>
                    <Link to="/register">Register</Link>
                    <Link to="/login">Login</Link>
                </>
            )}
        </nav>
    );
}
