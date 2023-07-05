import { Link } from "react-router-dom";

export default function NotFound() {
    return (
        <>
            <h1>Page Not Found</h1>
            <p>
                Back to <Link to="/">home</Link>.
            </p>
        </>
    );
}
