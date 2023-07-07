import { Link } from "react-router-dom";
import { logoutUser } from "../api";
import { useStore } from "../store";

export default function Navigation() {
    const user = useStore((state) => state.user);
    const clearUser = useStore((state) => state.clearUser);

    const onLogout = () => {
        logoutUser().then(() => clearUser());
    };

    return (
        <nav>
            <Link to="/">Home</Link>
            {user ? (
                <>
                    <Link to="/vote">Vote</Link>
                    <Link to="/votes">Votes</Link>
                    { user.isAdmin && <Link to="/quotes">Quotes</Link> }
                    { user.isAdmin && <Link to="/quotes/new">New Quote</Link> }
                    <button onClick={onLogout}>Logout</button>
                    <div>{user.nickname}</div>
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
