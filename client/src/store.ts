import { create } from "zustand";
import { GetSessionResponse } from "./types";
import * as api from "./api";

export interface Store {
    user: GetSessionResponse | null;

    setUser: (user: GetSessionResponse) => void;
    clearUser: () => void;
}

export const useStore = create<Store>((set) => ({
    user: setupCurrentUser(set),

    setUser: (user) => set({ user }),
    clearUser: () => set({ user: null }),
}));

type StoreSetter = (
    setter: Partial<Store> | ((state: Store) => Partial<Store>),
) => void;

function setupCurrentUser(set: StoreSetter) {
    api.currentUser().then((user) => user && set({ user }));
    return null;
}
