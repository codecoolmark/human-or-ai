export interface GetSessionResponse {
    email: string;
    nickname: string;
    isAdmin: boolean;
}

export interface RegisterData {
    email: string;
    nickname: string;
    password: string;
}

export interface LoginData {
    userName: string;
    password: string;
}

export interface Quote {
    id: number;
    text: string;
    isReal: boolean;
    expires: string;
}

export interface Vote {
    id: number;
    quoteId: number;
    created: string;
    isReal: boolean;
}

export interface PostVoteRequest {
    quoteId: number;
    isReal: boolean;
}

export interface VoteAndQuoteText {
    text: string;
    isReal: boolean;
    /** Is `null` if this quote has not yet been revealed */
    isCorrect: boolean | null;
    created: string;
}

export type Result<T> =
    | {
          isOk: true;
          value: T;
          error: null;
      }
    | {
          isOk: false;
          value: null;
          error: string;
      };

export interface GeneratedQuote {
    quote: string;
}

export class ResponseError implements Error {
    name = "RequestError";
    message: string;
    response: Response;

    constructor(message: string, response: Response) {
        this.message = message;
        this.response = response;
    }
}

export interface RegisterErrors {
    isUsernameExists: boolean
    isEmailExists: boolean
}
