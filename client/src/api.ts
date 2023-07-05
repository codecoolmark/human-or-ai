import { LoginData, Quote, Result, UserData } from "./types";

const server = new URL(import.meta.env.VITE_SERVER_URL);

function fetchJson(endpoint: string, options = {}) {
    return fetch(new URL(endpoint, server), {
        method: "GET",
        credentials: "include",
        headers: {
            "Content-Type": "application/json",
        },
        ...options,
    }).then((response) => {
        if (response.ok) {
            return response.json();
        }
        console.error(response);
        throw new Error("Something went wrong");
    });
}

export async function registerUser(
    login: LoginData,
): Promise<Result<UserData>> {
    return fetchJson("/users/register", {
        method: "POST",
        body: JSON.stringify(login),
    });
}

export async function loginUser(login: LoginData): Promise<UserData> {
    return fetchJson("/users/login", {
        method: "POST",
        body: JSON.stringify(login),
    });
}

export function getQuotes(): Promise<Quote[]> {
    return fetchJson("/quotes");
}

export function createQuote(quote: Omit<Quote, "id">) {
    return fetchJson("/quotes", {
        method: "POST",
        body: JSON.stringify(quote),
    });
}

export function getVotes() {
    return fetchJson("/votes");
}
