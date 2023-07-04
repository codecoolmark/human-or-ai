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
