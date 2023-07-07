import {
    LoginData,
    PostVoteRequest,
    Quote,
    RegisterData,
    Result,
    UserData,
    Vote,
    VoteAndQuoteText,
} from "./types";

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
        if (res.ok) return;
        console.error(res);
        throw new Error("Something went wrong");
    });
}

export function registerUser(user: RegisterData): Promise<Result<UserData>> {
    return fetchJson("/users", {
        method: "POST",
        body: JSON.stringify(user),
    });
}

export function loginUser(login: LoginData): Promise<UserData> {
    return fetchJson("/session", {
        method: "POST",
        body: JSON.stringify(login),
    });
}

export function logoutUser(): Promise<void> {
    return fetchVoid("/session", {
        method: "DELETE",
    });
}

export function currentUser(): Promise<UserData | null> {
    return fetchJson("/session");
}

export function getQuotes(): Promise<Quote[]> {
    return fetchJson("/quotes");
}

export function createQuote(quote: Omit<Quote, "id">): Promise<Quote> {
    return fetchJson("/quotes", {
        method: "POST",
        body: JSON.stringify(quote),
    });
}

export function getVotes(): Promise<VoteAndQuoteText[]> {
    return fetchJson("/votes");
}

export function createVote(vote: PostVoteRequest): Promise<Vote> {
    return fetchJson("/votes", {
        method: "POST",
        body: JSON.stringify(vote),
    });
}

export function quote(): Promise<Quote> {
    return fetchJson("/quote", {
        method: "POST",
    });
}
