import { Either } from "./Either";
import {
  LoginData,
  PostVoteRequest,
  Quote,
  RegisterData,
  GetSessionResponse,
  Vote,
  VoteAndQuoteText,
  ResponseError,
  RegisterErrors,
  GeneratedQuote
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
    throw new ResponseError("Server returned error response", response);
  });
}

function fetchVoid(endpoint: string, options: RequestInit = {}) {
  return fetch(new URL(endpoint, server), {
    credentials: "include",
    ...options,
  }).then((response) => {
    if (response.ok) {
      return response.json();
    }
    throw new ResponseError("Server returned error response", response);
  });
}

export async function registerUser(
  user: RegisterData
): Promise<Either<GetSessionResponse, RegisterErrors>> {
  const response = await fetch(new URL("/users", server), {
    credentials: "include",
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(user),
  });


  if (response.ok) {
    const user = await response.json();
    return new Either({ a: user })
  } else if (response.status >= 400 && response.status <= 499) {
    const errors = await response.json()
    return new Either({ b: errors })
  }

  throw new ResponseError("Request failed", response);
}

export async function loginUser(login: LoginData): Promise<GetSessionResponse | null> {
  const response = await fetch(new URL("/session", server), {
    method: "POST",
    body: JSON.stringify(login),
    credentials: "include",
  });

  if (response.ok) {
    return await response.json();
  } else if (response.status === 403) {
    return null;
  }

  throw new ResponseError("Request failed", response);
}

export function logoutUser(): Promise<void> {
  return fetchVoid("/session", {
    method: "DELETE",
  });
}

export function currentUser(): Promise<GetSessionResponse | null> {
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

export function generateQuote(): Promise<GeneratedQuote> {
    return fetchJson("/quotes/generate", {
        method: "POST"
    })
}
