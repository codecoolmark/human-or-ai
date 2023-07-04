import { LoginData, UserData } from "./types";

const server = new URL(import.meta.env.VITE_SERVER_URL);

export async function registerUser(login: LoginData): Promise<UserData> {
    const res = await fetch(new URL("/users", server), {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(login),
    });

    if (!res.ok) {
        const msg =
            res.status === 403
                ? "Email may already be registered"
                : res.status.toString();
        throw new Error(msg);
    }

    return await res.json();
}

export async function loginUser(login: LoginData): Promise<UserData> {
    const res = await fetch(new URL("/users/login", server), {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(login),
    });

    if (!res.ok) {
        const msg =
            res.status === 403 ? "Invalid login data" : res.status.toString();
        throw new Error(msg);
    }

    return await res.json();
}

export function getQuotes() {
    return fetch(new URL("/quotes", server)).then((response) =>
        response.json(),
    );
}
