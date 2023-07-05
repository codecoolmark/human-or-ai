import { create } from "zustand";
import { UserData } from "./types";

export interface Store {
    user: UserData | null;

    setUser: (user: UserData) => void;
    clearUser: () => void;
}

export const useStore = create<Store>((set) => ({
    user: null,

    setUser: (user) => set({ user }),
    clearUser: () => set({ user: null }),
}));
