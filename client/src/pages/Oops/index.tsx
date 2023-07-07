import { useRouteError } from "react-router"

export default function Oops() {
    const error = useRouteError()
    console.error(error);
    return <main>
        <h1>Oopy something went wrong</h1>
        <p>{error.message}</p>
    </main>
}