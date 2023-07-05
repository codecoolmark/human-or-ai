import { LoginData, Result, UserData } from "./types";

const server = new URL(import.meta.env.VITE_SERVER_URL);

function fetchJson(endpoint: string, options = {}) {
    return fetch(new URL(endpoint, server), {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
        },
        ...options,
    }).then((response) => {
        if (response.ok) {
            return response.json();
        }
        throw new Error("Something went wrong");
    });
}

export async function registerUser(
    login: LoginData,
): Promise<Result<UserData>> {
    return fetchJson("/users", {
        method: "POST",
        body: JSON.stringify(login),
    });
}

export async function loginUser(login: LoginData): Promise<Result<UserData>> {
    return fetchJson("/users/login", {
        method: "POST",
        body: JSON.stringify(login),
    });
}

export function getQuotes() {
    return fetchJson("/quotes");
}

export function createQuote(quote) {
    return fetchJson("/quotes", {
        method: "POST",
        body: JSON.stringify(quote),
    });
}

export function getVotes() {
    return fetchJson("/votes");
}
