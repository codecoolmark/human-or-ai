export interface UserData {
    id: number;
    email: string;
    nickname: string;
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
    userId: number;
    isReal: boolean;
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
