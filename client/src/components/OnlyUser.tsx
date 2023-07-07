import { useStore } from "../store";

export default function OnlyUser({ children }: { children: JSX.Element | JSX.Element[] }) {
    const user = useStore((state) => state.user);

    if (user === null) {
        return <></>
    }

    return <>{children}</>

}