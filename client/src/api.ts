import { LoginData, Quote, Result, UserData } from "./types";

const server = new URL(import.meta.env.VITE_SERVER_URL);

function fetchJson(endpoint: string, options: RequestInit = {}) {
    return fetch(new URL(endpoint, server), {
        method: "GET",
        credentials: "include",
        headers: {
            "Content-Type": "application/json",
            ...options.headers,
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

function fetchVoid(endpoint: string, options: RequestInit = {}) {
    return fetch(new URL(endpoint, server), {
        credentials: "include",
        ...options,
    }).then((res) => {
        console.log(res);
        if (res.ok) return;
        console.error(res);
        throw new Error("Something went wrong");
    });
}

export function registerUser(login: LoginData): Promise<Result<UserData>> {
    return fetchJson("/users/register", {
        method: "POST",
        body: JSON.stringify(login),
    });
}

export function loginUser(login: LoginData): Promise<UserData> {
    return fetchJson("/users/login", {
        method: "POST",
        body: JSON.stringify(login),
    });
}

export function logoutUser(): Promise<void> {
    return fetchVoid("/users/logout", {
        method: "DELETE",
    });
}

export function currentUser(): Promise<UserData | null> {
    return fetchJson("/users/current");
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
