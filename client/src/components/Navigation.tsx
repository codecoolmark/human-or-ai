import { Link } from "react-router-dom";
import { logoutUser } from "../api";
import { useStore } from "../store";
import OnlyUser from "./OnlyUser";
import OnlyAdmin from "./OnlyAdmin";
import OnlyAnonymous from "./OnlyAnonymous";

export default function Navigation() {
    const user = useStore((state) => state.user);
    const clearUser = useStore((state) => state.clearUser);

    const onLogout = () => {
        logoutUser().then(() => clearUser());
    };

    return (
        <nav>
            <menu className="menu">
                <li><Link to="/">Home</Link></li>
                <OnlyUser>
                    <li><Link to="/vote">Vote</Link></li>
                    <li><Link to="/votes">Votes</Link></li>
                </OnlyUser>
                <OnlyAdmin>
                    <li><Link to="/quotes">Quotes</Link></li>
                    <li><Link to="/quotes/new">New Quote</Link></li>
                </OnlyAdmin>
                <OnlyAnonymous>
                    <li><Link to="/register">Register</Link></li>
                </OnlyAnonymous>
            </menu>

            <div className="user-panel">
                <OnlyAnonymous>
                    <Link to="/login">Login</Link>
                </OnlyAnonymous>
                <OnlyUser>
                        <div>Hello {user?.nickname}</div>
                        <button className="link" onClick={onLogout}>Logout</button>
                </OnlyUser>
            </div>
        </nav>
    );
}
