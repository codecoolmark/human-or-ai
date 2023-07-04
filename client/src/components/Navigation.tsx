import { Link } from "react-router-dom";
import { useStore } from "../store";

export default function Navigation() {
    const user = useStore((state) => state.user);
    const clearUser = useStore((state) => state.clearUser);

    return (
        <nav>
            <Link to="/">Home</Link>
            {user ? (
                <>
                    <Link to="/quotes">Quotes</Link>
                    <Link to="/quotes/new">New Quote</Link>
                    <button onClick={clearUser}>Logout</button>
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
