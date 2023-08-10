import { useRouteError } from "react-router"

interface OopsProbs {
    exception?: Error
}

export default function Oops({ exception }: OopsProbs) {
    const routerError = useRouteError() as { error: Error };
    console.error(exception ?? routerError);

    const message = exception?.message ?? routerError?.error.message;

    return <main>
        <h1>Oopy something went wrong</h1>
        <p className="error">Reload the page and try again.</p>
        {message && 
            <details>
                <summary>Detailed error message</summary>
                {message}
            </details>}
    </main>
}