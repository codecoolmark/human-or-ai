import { GetSessionResponse } from "./types";

export function authorizeAdmin(user: GetSessionResponse | null): void {
    if (user?.isAdmin !== true) {
        throw new Error("Unauthorized");
    }
}