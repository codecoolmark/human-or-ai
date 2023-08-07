import { useRouteError } from "react-router"

export default function Oops() {
    const error = useRouteError() as {message: string | undefined};

    console.error(error);
    return <main>
        <h1>Oopy something went wrong</h1>
        <p>{error.message}</p>
    </main>
}