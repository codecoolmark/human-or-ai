import { create } from "zustand";
import { GetSessionResponse } from "./types";
import * as api from "./api";

export interface Store {
    user: GetSessionResponse | null;
    exception: Error | null;

    setUser: (user: GetSessionResponse) => void;
    clearUser: () => void;

    setException: (exception: Error) => void;
}

export const useStore = create<Store>((set) => ({
    user: setupCurrentUser(set),
    exception: null,

    setUser: (user) => set({ user }),
    clearUser: () => set({ user: null }),
    setException: (exception) => set({ exception })
}));

type StoreSetter = (
    setter: Partial<Store> | ((state: Store) => Partial<Store>),
) => void;

function setupCurrentUser(set: StoreSetter) {
    api.currentUser().then((user) => user && set({ user }));
    return null;
}
