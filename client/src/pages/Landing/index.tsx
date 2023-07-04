import { Link } from "react-router-dom";

export default function LandingPage() {
    return (
        <main>
            <h1>Human or AI?</h1>
            <p>
                Can you guess which quote is a real excerpt and which was
                AI-generated?
            </p>
            <p>
                <Link to="/register">Register</Link> your user account or{" "}
                <Link to="/login">login</Link> to get started.
            </p>
        </main>
    );
}
