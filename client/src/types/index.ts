export interface UserData {
    id: number;
    email: string;
    nickname: string;
    passwordHash: string;
}

export interface LoginData {
    email: string;
    nickname: string;
    password: string;
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
